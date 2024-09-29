package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
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
public class S02PacketChat implements Packet<INetHandlerPlayClient> {
	private IChatComponent chatComponent;
	private byte type;

	public S02PacketChat() {
	}

	public S02PacketChat(IChatComponent component) {
		this(component, (byte) 1);
	}

	public S02PacketChat(IChatComponent message, byte typeIn) {
		this.chatComponent = message;
		this.type = typeIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.chatComponent = parPacketBuffer.readChatComponent();
		this.type = parPacketBuffer.readByte();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeChatComponent(this.chatComponent);
		parPacketBuffer.writeByte(this.type);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleChat(this);
	}

	public IChatComponent getChatComponent() {
		return this.chatComponent;
	}

	public boolean isChat() {
		return this.type == 1 || this.type == 2;
	}

	/**+
	 * Returns the id of the area to display the text, 2 for above
	 * the action bar, anything else currently for the chat window
	 */
	public byte getType() {
		return this.type;
	}
}