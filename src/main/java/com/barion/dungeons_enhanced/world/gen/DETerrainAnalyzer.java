package com.barion.dungeons_enhanced.world.gen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.function.Predicate;

public class DETerrainAnalyzer {
    /**
     * @return whether the average height difference between the corner points are smaller than the threshold and the average height, always true on {@link FlatLevelSource}
     */
    public static Pair<Float, Boolean> isFlatEnough(BlockPos pos, Vec3i size, int padding, int threshold, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState){
        if(generator instanceof FlatLevelSource) {return Pair.of((float)generator.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState), true);}
//        if(!(heightAccessor instanceof ServerLevel level)) {return Pair.of(0f, false);}

        int x1 = pos.getX()+padding;
        int x2 = pos.getX()+size.getX()-padding;
        int z1 = pos.getZ()+padding;
        int z2 = pos.getZ()+size.getZ()-padding;


        int height1 = generator.getBaseHeight(x1, z1, Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState);
//        level.setBlockAndUpdate(new BlockPos(x1, height1, z1), Blocks.RED_WOOL.defaultBlockState());
        int height2 = generator.getBaseHeight(x2, z1, Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState);
//        level.setBlockAndUpdate(new BlockPos(x2, height2, z1), Blocks.RED_WOOL.defaultBlockState());
        int height3 = generator.getBaseHeight(x2, z2, Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState);
//        level.setBlockAndUpdate(new BlockPos(x2, height3, z2), Blocks.RED_WOOL.defaultBlockState());
        int height4 = generator.getBaseHeight(x1, z2, Heightmap.Types.OCEAN_FLOOR_WG, heightAccessor, randomState);
//        level.setBlockAndUpdate(new BlockPos(x1, height4, z2), Blocks.RED_WOOL.defaultBlockState());

        float averageHeight = (height1+height2+height3+height4)/4f;
        float averageHeightDifference = (Math.abs(averageHeight-height1)+Math.abs(averageHeight-height2)+Math.abs(averageHeight-height3)+Math.abs(averageHeight-height4))/4f;
        return Pair.of(averageHeight, averageHeightDifference < threshold);
    }

    public static boolean isUnderwater(BlockPos pos, ChunkGenerator generator, int depth, LevelHeightAccessor heightAccessor, RandomState randomState) {
        return getBlockAt(pos.above(depth), generator, heightAccessor, randomState).is(Blocks.WATER);
    }

    public static boolean areNearbyBiomesValid(BiomeSource biomeSource, BlockPos pos, ChunkGenerator generator, int radius, Predicate<Holder<Biome>> biomePredicate, RandomState randomState){
        for(Holder<Biome> biome : biomeSource.getBiomesWithin(pos.getX(), generator.getSeaLevel(), pos.getZ(), radius, randomState.sampler())) {
            if (!biomePredicate.test(biome)) {return false;}
        }
        return true;
    }

    public static BlockState getBlockAt(BlockPos pos, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState) {return generator.getBaseColumn(pos.getX(), pos.getZ(), heightAccessor, randomState).getBlock(pos.getY());}
}