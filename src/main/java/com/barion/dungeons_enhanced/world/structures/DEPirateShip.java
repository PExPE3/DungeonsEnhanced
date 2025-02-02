package com.barion.dungeons_enhanced.world.structures;

import com.barion.dungeons_enhanced.DEStructures;
import com.barion.dungeons_enhanced.DEUtil;
import com.barion.dungeons_enhanced.world.structures.prefabs.DESwimmingStructure;
import com.barion.dungeons_enhanced.world.structures.prefabs.utils.DEPieceAssembler;
import com.barion.dungeons_enhanced.world.structures.prefabs.utils.DEStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.barion.dungeons_enhanced.DEUtil.location;

public class DEPirateShip extends DESwimmingStructure {
    private static final ResourceLocation Back = location("pirate_ship/back");
    public DEPirateShip(StructureSettings settings) {super(settings, DEUtil.pieceBuilder().yOffset(-3).add("pirate_ship/front").build(), DEPirateShip::assembleShip, DEStructures.PirateShip::getType);}

    @Override @Nonnull
    public Optional<GenerationStub> findGenerationPoint(@Nonnull GenerationContext context) {
        final DEStructurePieces.Piece piece = variants.getRandomPiece(context.random());
        final BlockPos pos = getGenPos(context).above(piece.yOffset);

        return at(pos, (builder) -> generatePieces(builder, pos, piece, Rotation.NONE, context, DEPirateShip::assembleShip));
    }

    public static void assembleShip(DEPieceAssembler.Context context) {
        context.piecesBuilder().addPiece(new Piece(context.structureManager(), context.piece(), context.pos(), context.rotation()));
        context.piecesBuilder().addPiece(new Piece(context.structureManager(), Back, context.pos().offset(0, 0, 25), context.rotation()));
    }
}