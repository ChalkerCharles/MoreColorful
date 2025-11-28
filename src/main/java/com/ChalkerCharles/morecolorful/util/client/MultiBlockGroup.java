package com.ChalkerCharles.morecolorful.util.client;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MultiBlockGroup {
    private static final Predicate<BlockState> GROUP_CHECK = IBlockStateExtension::isGroupBlock;
    private static final Predicate<BlockState> NEVER = state -> false;
    private final SortedGroups[] allGroups = new SortedGroups[256];
    private final Groups[] groupsBySection;
    private final int minSection;

    public MultiBlockGroup(ChunkAccess chunk) {
        this.groupsBySection = new Groups[chunk.getSectionsCount()];
        int minSection = this.minSection = chunk.getMinSection();
        int maxSection = chunk.getMaxSection();
        LevelChunkSection[] sections = filterValidSections(chunk);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                this.buildGroups(sections, height, x, z, minSection, maxSection);
                this.fillGroupsBySection(x, z);
            }
        }
    }

    private static LevelChunkSection[] filterValidSections(ChunkAccess chunk) {
        LevelChunkSection[] sections = chunk.getSections().clone();
        for (int i = 0, j = chunk.getSectionsCount(); i < j; i++) {
            LevelChunkSection section = sections[i];
            if (section.hasOnlyAir() || !section.maybeHas(GROUP_CHECK)) {
                sections[i] = null;
            }
        }
        return sections;
    }

    private void buildGroups(LevelChunkSection[] sections, int height, int x, int z, int minSection, int maxSection) {
        Group lastGroup = null;
        for (int i = minSection; i < maxSection; i++) {
            LevelChunkSection section = sections[i - minSection];
            if (section == null) continue;
            int origin = i << 4;
            for (int j = 0; j < 16; j++) {
                int y = j + origin;
                if (y > height) return;
                BlockState state = section.getBlockState(x, j, z);
                if (lastGroup != null && lastGroup.canGrow(state, y)) {
                    lastGroup.high++;
                } else {
                    lastGroup = GROUP_CHECK.test(state) ? this.getGroup(state, x, y, z) : null;
                }
            }
        }
    }

    private void fillGroupsBySection(int x, int z) {
        SortedGroups groups = this.allGroups[packPos(x, z)];
        if (groups == null) return;
        for (Group group : groups) {
            int a = group.getLowSection(), b = group.getHighSection();
            for (int i = a; i <= b; i++) {
                this.getGroupsInSection(i).add(group);
            }
        }
    }

    @Nullable
    public Groups getGroupsNullableInSection(int sectionY) {
        int i = sectionY - this.minSection;
        return groupsBySection[i];
    }

    public void tryUpdate(BlockPos blockPos, BlockState oldState, BlockState newState, boolean isPrevGroup, boolean isNowGroup) {
        int x = blockPos.getX() & 15, y = blockPos.getY(), z = blockPos.getZ() & 15;
        SortedGroups groups = this.getGroups(x, z);
        if (isPrevGroup && isNowGroup) {
            this.updateBothGroups(groups, oldState, newState, y);
        } else if (isNowGroup) {
            this.updateNewGroup(groups, newState, y);
        } else {
            this.updateOldGroup(groups, oldState, y);
            this.clearEmptyGroups(groups, x, y, z);
        }
    }

    private void updateBothGroups(SortedGroups groups, BlockState oldState, BlockState newState, int y) {
        if (sameType(oldState, newState)) return;
        Group oldGroup = groups.getGroup(oldState, y);
        int low = oldGroup.low, high = oldGroup.high;
        if (y == low) {
            Group g = groups.getGroupNullable(newState, y - 1);
            if (g != null) {
                g.high++;
                if ((y & 15) == 0) {
                    this.addGroupToSection(y, g);
                }
            } else {
                int i = groups.indexOf(oldGroup);
                Group g1 = new Group(newState, y);
                groups.add(i, g1);
                this.addGroupToSection(y, g1);
            }
            oldGroup.low++;
            if ((y & 15) == 15) {
                this.removeGroupFromSection(y, oldGroup);
            }
            if (oldGroup.isInvalid()) {
                groups.remove(oldGroup);
                this.removeGroupFromSection(y, oldGroup);
            }
        } else if (y == high) {
            Group g = groups.getGroupNullable(newState, y + 1);
            if (g != null) {
                g.low--;
                if ((y & 15) == 15) {
                    this.addGroupToSection(y, g);
                }
            } else {
                int i = groups.indexOf(oldGroup);
                Group g1 = new Group(newState, y);
                groups.add(i + 1, g1);
                this.addGroupToSection(y, g1);
            }
            oldGroup.high--;
            if ((y & 15) == 0) {
                this.removeGroupFromSection(y, oldGroup);
            }
            if (oldGroup.isInvalid()) {
                groups.remove(oldGroup);
                this.removeGroupFromSection(y, oldGroup);
            }
        } else {
            Group g1 = new Group(newState, y);
            Group g2 = new Group(oldGroup.predicate, y + 1, oldGroup.high);
            this.removeGroupFromSections(y - 1, y, oldGroup.high, oldGroup);
            oldGroup.high = y - 1;
            int i = groups.indexOf(oldGroup);
            groups.add(i + 1, g1);
            groups.add(i + 2, g2);
            this.addGroupToSection(y, g1);
            this.addGroupToSections(g2);
        }
    }

    private void updateNewGroup(SortedGroups groups, BlockState newState, int y) {
        Group g1 = groups.getGroupNullable(newState, y - 1);
        Group g2 = groups.getGroupNullable(newState, y + 1);
        if (g1 != null && g2 != null) {
            g1.high = g2.high;
            groups.remove(g2);
            this.addGroupToSections(y - 1, y, g1.high, g1);
            this.removeGroupFromSections(g2);
        } else if (g2 != null) {
            g2.low--;
            if ((y & 15) == 15) {
                this.addGroupToSection(y, g2);
            }
        } else if (g1 != null) {
            g1.high++;
            if ((y & 15) == 0) {
                this.addGroupToSection(y, g1);
            }
        } else {
            Group g = new Group(newState, y);
            groups.add(g);
            this.addGroupToSection(y, g);
        }
    }

    private void updateOldGroup(SortedGroups groups, BlockState oldState, int y) {
        Group oldGroup = groups.getGroup(oldState, y);
        int low = oldGroup.low, high = oldGroup.high;
        if (y == low) {
            oldGroup.low++;
            if ((y & 15) == 15) {
                this.removeGroupFromSection(y, oldGroup);
            }
            if (oldGroup.isInvalid()) {
                groups.remove(oldGroup);
                this.removeGroupFromSection(y, oldGroup);
            }
        } else if (y == high) {
            oldGroup.high--;
            if ((y & 15) == 0) {
                this.removeGroupFromSection(y, oldGroup);
            }
            if (oldGroup.isInvalid()) {
                groups.remove(oldGroup);
                this.removeGroupFromSection(y, oldGroup);
            }
        } else {
            Group g = new Group(oldGroup.predicate, y + 1, oldGroup.high);
            this.removeGroupFromSections(y - 1, y, oldGroup.high, oldGroup);
            oldGroup.high = y - 1;
            int i = groups.indexOf(oldGroup);
            groups.add(i + 1, g);
            this.addGroupToSections(g);
        }
    }

    private void clearEmptyGroups(SortedGroups groups, int x, int y, int z) {
        if (groups.isEmpty()) {
            allGroups[packPos(x, z)] = null;
        }
        int i = (y >> 4) - this.minSection;
        Groups g = groupsBySection[i];
        if (g != null && g.isEmpty()) {
            groupsBySection[i] = null;
        }
    }

    private SortedGroups getGroups(int x, int z) {
        int i = packPos(x, z);
        SortedGroups groups = allGroups[i];
        if (groups == null) {
            groups = new SortedGroups();
            allGroups[i] = groups;
        }
        return groups;
    }

    private Group getGroup(BlockState state, int x, int y, int z) {
        return getGroups(x, z).getGroup(state, y);
    }

    private Groups getGroupsInSection(int sectionY) {
        int i = sectionY - this.minSection;
        Groups groups = groupsBySection[i];
        if (groups == null) {
            groups = new Groups();
            groupsBySection[i] = groups;
        }
        return groups;
    }

    private void addGroupToSection(int y, Group group) {
        this.getGroupsInSection(y >> 4).add(group);
    }

    private void removeGroupFromSection(int y, Group group) {
        this.getGroupsInSection(y >> 4).remove(group);
    }

    private void addGroupToSections(Group group) {
        int a = group.getLowSection(), b = group.getHighSection();
        for (int i = a; i <= b; i++) {
            this.getGroupsInSection(i).add(group);
        }
    }

    private void removeGroupFromSections(Group group) {
        int a = group.getLowSection(), b = group.getHighSection();
        for (int i = a; i <= b; i++) {
            this.getGroupsInSection(i).remove(group);
        }
    }

    private void addGroupToSections(int y, int low, int high, Group group) {
        int a = low >> 4, b = high >> 4, c = y >> 4;
        for (int i = a; i <= b; i++) {
            if (i == c) continue;
            this.getGroupsInSection(i).add(group);
        }
    }

    private void removeGroupFromSections(int y, int low, int high, Group group) {
        int a = low >> 4, b = high >> 4, c = y >> 4;
        for (int i = a; i <= b; i++) {
            if (i == c) continue;
            this.getGroupsInSection(i).remove(group);
        }
    }

    private static boolean sameType(BlockState state1, BlockState state2) {
        return getPredicate(state1).test(state2);
    }

    private static Predicate<BlockState> getPredicate(BlockState state) {
        for (Predicate<BlockState> predicate : WavyBlockUtils.MULTI_BLOCK_GROUPS) {
            if (predicate.test(state)) return predicate;
        }
        return NEVER;
    }

    private static int packPos(int x, int z) {
        return ((z << 4) | x) & 255;
    }

    public static class Group implements Comparable<Group> {
        private final Predicate<BlockState> predicate;
        private int low;
        private int high;

        private Group(Predicate<BlockState> predicate, int low, int high) {
            this.predicate = predicate;
            this.low = low;
            this.high = high;
        }

        private Group(BlockState state, int low, int high) {
            this(getPredicate(state), low, high);
        }

        private Group(BlockState state, int low) {
            this(state, low, low);
        }

        public int getLowSection() {
            return this.low >> 4;
        }

        public int getHighSection() {
            return this.high >> 4;
        }

        @Override
        public int compareTo(Group other) {
            return Integer.compare(this.low, other.low);
        }

        private boolean test(BlockState state) {
            return this.predicate.test(state);
        }

        private boolean isInvalid() {
            return this.low > this.high;
        }

        private boolean canGrow(BlockState state, int y) {
            return predicate.test(state) && high <= y;
        }
    }

    public static class Groups extends ArrayList<Group> {}

    private static class SortedGroups extends ArrayList<Group> {
        private boolean isSorted = true;

        private SortedGroups() {
            super(2);
        }

        private Group getGroup(BlockState state, int y) {
            if (!this.isSorted) this.sort();
            int idx = this.binarySearch(y);
            Group group;
            if (idx >= 0) {
                group = this.get(idx);
                if (!group.test(state))
                    throw new IllegalStateException("Found group, but state didn't match: " + state);
            } else {
                group = new Group(state, y);
                this.add(~idx, group);
            }
            return group;
        }

        @Nullable
        private Group getGroupNullable(BlockState state, int y) {
            if (!this.isSorted) this.sort();
            int idx = this.binarySearch(y);
            if (idx >= 0) {
                Group group = this.get(idx);
                if (group.test(state)) {
                    return group;
                }
            }
            return null;
        }

        private void sort() {
            this.isSorted = true;
            this.sort(null);
        }

        @Override
        public boolean add(Group group) {
            this.isSorted = false;
            return super.add(group);
        }

        private int binarySearch(int y) {
            int low = 0;
            int high = this.size() - 1;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                Group group = this.get(mid);
                if (group.high < y)
                    low = mid + 1;
                else if (group.low > y)
                    high = mid - 1;
                else
                    return mid;
            }
            return ~low;
        }
    }
}
