package io.izzel.taboolib.util.book.builder;

import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.chat.BaseComponent;
import io.izzel.taboolib.util.chat.TextComponent;

/**
 * @author sky
 * @since 2018-03-08 22:36:58
 */
public class PageBuilder {

    private BaseComponent[] text = TextComponent.fromLegacyText("");

    /**
     * Adds a simple black-colored text to the page
     *
     * @param text the text to add
     * @return the PageBuilder's calling instance
     */
    public io.izzel.taboolib.util.book.builder.PageBuilder add(String text) {
        java.util.Arrays.stream(TextComponent.fromLegacyText(text)).forEach(component -> this.text = ArrayUtil.arrayAppend(this.text, component));
        return this;
    }

    /**
     * Adds a component to the page
     *
     * @param component the component to add
     * @return the PageBuilder's calling instance
     */
    public io.izzel.taboolib.util.book.builder.PageBuilder add(BaseComponent component) {
        this.text = ArrayUtil.arrayAppend(this.text, component);
        return this;
    }

    /**
     * Adds one or more components to the page
     *
     * @param components the components to add
     * @return the PageBuilder's calling instance
     */
    public io.izzel.taboolib.util.book.builder.PageBuilder add(BaseComponent... components) {
        java.util.Arrays.stream(components).forEach(component -> this.text = ArrayUtil.arrayAppend(this.text, component));
        return this;
    }

    /**
     * Adds a newline to the page (equivalent of adding \n to the previous component)
     *
     * @return the PageBuilder's calling instance
     */
    public io.izzel.taboolib.util.book.builder.PageBuilder newLine() {
        return add("\n");
    }

    /**
     * Another way of newLine(), better resolution (equivalent of adding \n to the previous component)
     *
     * @return the PageBuilder's calling instance
     */
    public io.izzel.taboolib.util.book.builder.PageBuilder endLine() {
        return newLine();
    }

    /**
     * Builds the page
     *
     * @return an array of BaseComponents representing the page
     */
    public BaseComponent[] build() {
        return text;
    }

    /**
     * Creates a new PageBuilder instance wih the parameter as the initial text
     *
     * @param text the initial text of the page
     * @return a new PageBuilder with the parameter as the initial text
     */
    public static io.izzel.taboolib.util.book.builder.PageBuilder of(String text) {
        return new io.izzel.taboolib.util.book.builder.PageBuilder().add(text);
    }

    /**
     * Creates a new PageBuilder instance wih the parameter as the initial component
     *
     * @param text the initial component of the page
     * @return a new PageBuilder with the parameter as the initial component
     */
    public static io.izzel.taboolib.util.book.builder.PageBuilder of(BaseComponent text) {
        return new io.izzel.taboolib.util.book.builder.PageBuilder().add(text);
    }

    /**
     * Creates a new PageBuilder instance wih the parameter as the initial components
     *
     * @param text the initial components of the page
     * @return a new PageBuilder with the parameter as the initial components
     */
    public static io.izzel.taboolib.util.book.builder.PageBuilder of(BaseComponent... text) {
        io.izzel.taboolib.util.book.builder.PageBuilder res = new io.izzel.taboolib.util.book.builder.PageBuilder();
        for (BaseComponent b : text) {
            res.add(b);
        }
        return res;
    }
}