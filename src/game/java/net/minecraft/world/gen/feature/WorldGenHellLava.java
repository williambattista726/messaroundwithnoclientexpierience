package net.minecraft.world.gen.feature;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2024 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class WorldGenHellLava extends WorldGenerator {
	private final Block field_150553_a;
	private final boolean field_94524_b;

	public WorldGenHellLava(Block parBlock, boolean parFlag) {
		this.field_150553_a = parBlock;
		this.field_94524_b = parFlag;
	}

	public boolean generate(World world, EaglercraftRandom random, BlockPos blockpos) {
		if (world.getBlockState(blockpos.up()).getBlock() != Blocks.netherrack) {
			return false;
		} else if (world.getBlockState(blockpos).getBlock().getMaterial() != Material.air
				&& world.getBlockState(blockpos).getBlock() != Blocks.netherrack) {
			return false;
		} else {
			int i = 0;
			if (world.getBlockState(blockpos.west()).getBlock() == Blocks.netherrack) {
				++i;
			}

			if (world.getBlockState(blockpos.east()).getBlock() == Blocks.netherrack) {
				++i;
			}

			if (world.getBlockState(blockpos.north()).getBlock() == Blocks.netherrack) {
				++i;
			}

			if (world.getBlockState(blockpos.south()).getBlock() == Blocks.netherrack) {
				++i;
			}

			if (world.getBlockState(blockpos.down()).getBlock() == Blocks.netherrack) {
				++i;
			}

			int j = 0;
			if (world.isAirBlock(blockpos.west())) {
				++j;
			}

			if (world.isAirBlock(blockpos.east())) {
				++j;
			}

			if (world.isAirBlock(blockpos.north())) {
				++j;
			}

			if (world.isAirBlock(blockpos.south())) {
				++j;
			}

			if (world.isAirBlock(blockpos.down())) {
				++j;
			}

			if (!this.field_94524_b && i == 4 && j == 1 || i == 5) {
				world.setBlockState(blockpos, this.field_150553_a.getDefaultState(), 2);
				world.forceBlockUpdateTick(this.field_150553_a, blockpos, random);
			}

			return true;
		}
	}
}