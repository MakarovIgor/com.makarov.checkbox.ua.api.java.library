package com.makarov.checkbox.ua.api.Models;

public class PngWidths {
    int width = 22, paperWidth = 50;

    public PngWidths() {
    }

    public PngWidths(int width, int paperWidth) {
        this.width = (width > 45) ? 45 : Math.max(width, 22);
        this.paperWidth = (paperWidth > 80) ? 80 : Math.max(paperWidth, 40);
    }

    public String urlParameters() {
        return "width=" + width + "&paper_width=" + paperWidth;
    }
}
