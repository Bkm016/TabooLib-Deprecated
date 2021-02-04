package io.izzel.taboolib.util.book;

import io.izzel.taboolib.Version;
import io.izzel.taboolib.kotlin.Reflex;
import io.izzel.taboolib.util.book.BookAsm;
import io.izzel.taboolib.util.chat.BaseComponent;
import io.izzel.taboolib.util.chat.ComponentSerializer;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftMetaBook;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * @Author sky
 * @Since 2019-08-18 16:46
 */
public class BookAsmImpl extends BookAsm {

    private final boolean v11600 = Version.isAfter(Version.v1_16);

    @Override
    public void setPages(BookMeta bookmeta, BaseComponent[]... pages) {
        ((CraftMetaBook) bookmeta).pages.clear();
        addPages(bookmeta, pages);
    }

    @Override
    public void addPages(BookMeta bookmeta, BaseComponent[]... pages) {
        for (BaseComponent[] components : pages) {
            if (v11600) {
                try {
                    ((org.bukkit.craftbukkit.v1_16_R1.inventory.CraftMetaBook) bookmeta).pages.add(net.minecraft.server.v1_16_R1.IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components)));
                } catch (Throwable ignored) {
                    List<String> p = Reflex.Companion.of(bookmeta).read("pages");
                    if (p != null) {
                        p.add(ComponentSerializer.toString(components));
                    }
                }
            } else {
                ((org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook) bookmeta).pages.add(net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components)));
            }
        }
    }
}
