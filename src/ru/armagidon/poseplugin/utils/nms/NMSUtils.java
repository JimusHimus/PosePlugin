package ru.armagidon.poseplugin.utils.nms;

import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.armagidon.poseplugin.utils.nms.impl.AnimationPlayer_1_14;
import ru.armagidon.poseplugin.utils.nms.impl.AnimationPlayer_1_15;
import ru.armagidon.poseplugin.utils.nms.impl.FakePlayer_1_14;
import ru.armagidon.poseplugin.utils.nms.impl.FakePlayer_1_15;

import java.util.ArrayList;
import java.util.List;

public class NMSUtils
{

    private SpigotVersion version;

    public NMSUtils() {
        version = SpigotVersion.currentVersion();
    }

    public enum SpigotVersion {
        VERSION_UNKNOWN,
        VERSION_1_14,
        VERSION_1_15;

        SpigotVersion() {}

        public static SpigotVersion currentVersion() {
            String version = Bukkit.getVersion();
            if (!version.endsWith("(MC: 1.15)") && !version.endsWith("(MC: 1.15.1)") && !version.endsWith("(MC: 1.15.2)")) {
                return !version.endsWith("(MC: 1.14)") && !version.endsWith("(MC: 1.14.1)") && !version.endsWith("(MC: 1.14.2)") && !version.endsWith("(MC: 1.14.3)") && !version.endsWith("(MC: 1.14.4)") ? VERSION_UNKNOWN : VERSION_1_14;
            } else {
                return VERSION_1_15;
            }
        }

        public static List<SpigotVersion> compatibleVersions() {
            List<SpigotVersion> versions = new ArrayList<>();
            SpigotVersion version = currentVersion();
            if (version == VERSION_UNKNOWN) {
                return versions;
            } else {
                versions.add(VERSION_1_14);
                if (version == VERSION_1_14) {
                    return versions;
                } else {
                    versions.add(VERSION_1_15);
                    return versions;
                }
            }
        }
    }

    public static FakePlayer getFakePlayerInstance(Player parent){
        switch (SpigotVersion.currentVersion()){
            case VERSION_1_14:
                return new FakePlayer_1_14(parent);
            case VERSION_1_15:
                return new FakePlayer_1_15(parent);
            default:
                throw new RuntimeException("Unsupportable version");
        }
    }

    public static void sendPacket(Player player, Packet<?> packet){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPacket(Player player, net.minecraft.server.v1_14_R1.Packet<?> packet){
        ((org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static AnimationPlayer getAnimationPlayer(){
        switch (SpigotVersion.currentVersion()){
            case VERSION_1_14:
                return new AnimationPlayer_1_14();
            case VERSION_1_15:
                return new AnimationPlayer_1_15();
            default:
                throw new RuntimeException("Unsupportable version");
        }
    }

}
