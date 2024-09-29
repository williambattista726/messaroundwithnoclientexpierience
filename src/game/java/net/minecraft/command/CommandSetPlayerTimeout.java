package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

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
public class CommandSetPlayerTimeout extends CommandBase {

	/**+
	 * Gets the name of the command
	 */
	public String getCommandName() {
		return "setidletimeout";
	}

	/**+
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel() {
		return 3;
	}

	/**+
	 * Gets the usage string for the command.
	 */
	public String getCommandUsage(ICommandSender var1) {
		return "commands.setidletimeout.usage";
	}

	/**+
	 * Callback when the command is invoked
	 */
	public void processCommand(ICommandSender parICommandSender, String[] parArrayOfString) throws CommandException {
		if (parArrayOfString.length != 1) {
			throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
		} else {
			int i = parseInt(parArrayOfString[0], 0);
			MinecraftServer.getServer().setPlayerIdleTimeout(i);
			notifyOperators(parICommandSender, this, "commands.setidletimeout.success",
					new Object[] { Integer.valueOf(i) });
		}
	}
}