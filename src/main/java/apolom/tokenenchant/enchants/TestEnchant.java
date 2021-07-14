package apolom.tokenenchant.enchants;

import com.vk2gpz.tokenenchant.api.EnchantHandler;
import com.vk2gpz.tokenenchant.api.InvalidTokenEnchantException;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class TestEnchant extends EnchantHandler {

    private final HashMap<Player, Integer> brokenBlocks = new HashMap<>();

    private HashMap<Player, Integer> getBrokenBlocks() {
        return brokenBlocks;
    }

    private String description;

    public TestEnchant(TokenEnchantAPI plugin) throws InvalidTokenEnchantException {
        this(plugin, null, null);
    }

    public TestEnchant(TokenEnchantAPI plugin, String name, FileConfiguration config) throws InvalidTokenEnchantException {
        super(plugin, name, config);
        loadConfig();
    }

    public void loadConfig() {
        super.loadConfig();
        this.description = this.config.getString("Enchants.Test.Description", "Description.");
    }

    public String getName() {
        return "Test";
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings[0].equalsIgnoreCase("testing")) {
            commandSender.sendMessage("123");
        }
        return super.onCommand(commandSender, command, s, strings);
    }

    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        final Block broken = e.getBlock();
        int level = getCELevel(p.getItemInHand());

        if (canExecute(level) && checkCooldown(p) && isValid(broken.getLocation())) {
            if (getBrokenBlocks().containsKey(e.getPlayer()))
                getBrokenBlocks().put(
                        e.getPlayer(),
                        getBrokenBlocks().get(e.getPlayer()) + 1
                );
            else
                getBrokenBlocks().put(e.getPlayer(), 1);
            p.sendMessage("Broken blocks: " + getBrokenBlocks().get(e.getPlayer()));
        }
    }
}