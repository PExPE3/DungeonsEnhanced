package com.barion.dungeons_enhanced.world.structures;

import com.barion.dungeons_enhanced.DEStructures;
import com.barion.dungeons_enhanced.DungeonsEnhanced;
import com.barion.dungeons_enhanced.world.DEJigsawTypes;
import com.barion.dungeons_enhanced.world.DEPools;
import com.legacy.structure_gel.api.structure.jigsaw.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class DELargeDungeon{
    /*private static boolean checkLocation(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context){
        if(context.validBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG)){
            return DETerrainAnalyzer.isFlatEnough(context.chunkPos(), context.chunkGenerator(), new DETerrainAnalyzer.Settings(1, 2, 2), context.heightAccessor(), context.randomState());
        }

        return false;
    }*/

    public static class Capability implements JigsawCapability.IJigsawCapability{
        public static final Capability Instance = new Capability();
        public static final Codec<Capability> CODEC = Codec.unit(Instance);

        @Override
        public JigsawCapability.JigsawType<?> getType(){return DEJigsawTypes.LargeDungeon;}
        @Override
        public IPieceFactory getPieceFactory() {return Piece::new;}
    }

    public static class Piece extends ExtendedJigsawStructurePiece {
        public Piece(IPieceFactory.Context context) {super(context);}
        public Piece(StructurePieceSerializationContext serializationContext, CompoundTag nbt) {super(serializationContext, nbt);}

        @Override
        public StructurePieceType getType() {return DEStructures.LargeDungeon.getPieceType().get();}
        @Override
        public void handleDataMarker(String key, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource random, BoundingBox box) {}
    }

    public static void pool(BootstapContext<StructureTemplatePool> context){
        JigsawRegistryHelper registry = new JigsawRegistryHelper(DungeonsEnhanced.ModID, "large_dungeon/", context);
        registry.registerBuilder().pools(registry.poolBuilder().names("root").maintainWater(false)).register(DEPools.LargeDungeon);

        JigsawPoolBuilder basicPool = registry.poolBuilder().maintainWater(false);
        JigsawPoolBuilder Cross = basicPool.clone().names("cross");
        JigsawPoolBuilder Rooms = basicPool.clone().names("room_small1", "room_small2", "room1", "room2", "room_big", "parkour", "storage");
        JigsawPoolBuilder Tunnels = basicPool.clone().names("tunnel");
        JigsawPoolBuilder Stairs = basicPool.clone().names("stairs");

        registry.register("cross", Cross.build());
        registry.register("main", JigsawPoolBuilder.collect(Tunnels.weight(4), Stairs.weight(2), Cross.weight(2), Rooms.weight(1)));
    }
}