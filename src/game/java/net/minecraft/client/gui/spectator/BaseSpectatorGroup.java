package net.minecraft.client.gui.spectator;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

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
public class BaseSpectatorGroup implements ISpectatorMenuView {
	private final List<ISpectatorMenuObject> field_178671_a = Lists.newArrayList();

	public BaseSpectatorGroup() {
		this.field_178671_a.add(new TeleportToPlayer());
		this.field_178671_a.add(new TeleportToTeam());
	}

	public List<ISpectatorMenuObject> func_178669_a() {
		return this.field_178671_a;
	}

	public IChatComponent func_178670_b() {
		return new ChatComponentText("Press a key to select a command, and again to use it.");
	}
}