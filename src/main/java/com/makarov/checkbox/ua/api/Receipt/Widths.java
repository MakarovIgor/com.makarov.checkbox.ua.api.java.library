package com.makarov.checkbox.ua.api.Receipt;

public class Widths {
    int width, paperWidth;

    public Widths(int countCharsInLine, int paperWidth) {
        this.width = countCharsInLine;
        this.paperWidth = paperWidth;
    }

    public String urlParameters() {
        return "width=" + width + "&paper_width=" + paperWidth;
    }
}
