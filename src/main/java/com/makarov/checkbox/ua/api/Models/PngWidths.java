package com.makarov.checkbox.ua.api.Models;

public class PngWidths {
    int width, paperWidth;

    public PngWidths(int width, int paperWidth) {
        this.width = width;
        this.paperWidth = paperWidth;
    }

    public String urlParameters() {
        return "width=" + width + "&paper_width=" + paperWidth;
    }
}
