package net.pl3x.map.plugin;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.pl3x.map.plugin.configuration.Config;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class Logging {
    private static final PlainTextComponentSerializer PLAIN_SERIALIZER = PlainTextComponentSerializer.builder()
        .flattener(PaperComponents.flattener())
        .build();

    private Logging() {
    }

    public static Logger logger() {
        return Pl3xMapPlugin.getInstance().getLog4JLogger();
    }

    public static void debug(final String msg) {
        if (Config.DEBUG_MODE) {
            logger().info("[DEBUG] " + msg);
        }
    }

    public static void warn(String msg, Throwable t) {
        logger().warn(msg, t);
    }

    public static void severe(String msg) {
        logger().error(msg);
    }

    public static void severe(String msg, Throwable t) {
        logger().error(msg, t);
    }

    public static void info(final String miniMessage, final Template... placeholders) {
        logger().info(
            PLAIN_SERIALIZER.serialize(
                MiniMessage.miniMessage().deserialize(
                    miniMessage,
                    TemplateResolver.templates(placeholders)
                )
            )
        );
    }
}
