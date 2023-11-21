package relleno;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel extends JComponent {

    private final BufferedImage bufferedPixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferedImage;

    Color sky = new Color(31, 141, 189);
    Color white = new Color(255, 255, 255);
    Color ladrillo = new Color(220, 90, 33);
    Color black = new Color(0, 0, 0);
    Color pastos = new Color(71, 172, 59);
    Color pastoSeco = new Color(145, 213, 27);
    Color moneda = new Color(255, 198, 2);
    Color ladrillo2 = new Color(47, 182, 163);
    Color tubo = new Color(15, 254, 0);
    Color rojo = new Color(255, 4, 0);
    Color piel = new Color(231, 209, 150);
    Color pelo = new Color(157, 127, 45);

    private static int[][] matrizPixels = {
        {0, 0, 0, 0, 0},
        {0, 1, 1, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 1, 1, 0},
        {0, 0, 0, 0, 0}
    };

    protected void putPixel(int x, int y, Color color) {
        bufferedPixel.setRGB(0, 0, color.getRGB());
        if (bufferedImage != null) {
            bufferedImage.getGraphics().drawImage(bufferedPixel, x, y, this);
        } else {
            getGraphics().drawImage(bufferedPixel, x, y, this);
        }
    }

    //Figuras
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            putPixel(Math.round(x), Math.round(y), color);
            x += xInc;
            y += yInc;
        }
    }

    public void drawCircle(int xc, int yc, int radius, Color color) {
        int x = 0;
        int y = radius;
        int d = 1 - radius;

        putPixel(xc + x, yc + y, color);
        putPixel(xc + x, yc - y, color);
        putPixel(xc - x, yc + y, color);
        putPixel(xc - x, yc - y, color);
        putPixel(xc + y, yc + x, color);
        putPixel(xc + y, yc - x, color);
        putPixel(xc - y, yc + x, color);
        putPixel(xc - y, yc - x, color);

        while (y > x) {
            if (d < 0) {
                d += 2 * x + 3;
                x++;
            } else {
                d += 2 * (x - y) + 5;
                x++;
                y--;
            }

            putPixel(xc + x, yc + y, color);
            putPixel(xc + x, yc - y, color);
            putPixel(xc - x, yc + y, color);
            putPixel(xc - x, yc - y, color);
            putPixel(xc + y, yc + x, color);
            putPixel(xc + y, yc - x, color);
            putPixel(xc - y, yc + x, color);
            putPixel(xc - y, yc - x, color);
        }
    }

    public void drawRectangle(int x, int y, int width, int height, Color color) {
        drawLine(x, y, x + width, y, color);
        drawLine(x, y, x, y + height, color);
        drawLine(x + width, y, x + width, y + height, color);
        drawLine(x, y + height, x + width, y + height, color);
    }

    public void drawRect(int x0, int y0, int x1, int y1, Color color) {
        //Top || D->I Bottom
        drawLine(x0, y0, x1, y0, color);
        //Bottom || D->I Top
        drawLine(x0, y1, x1, y1, color);
        //Right || D->I Left
        drawLine(x1, y0, x1, y1, color);
        //Left || D->I Right
        drawLine(x0, y0, x0, y1, color);
    }

    public void drawEllipse(int xc, int yc, int radiusX, int radiusY, Color color) {
        int x = 0;
        int y = radiusY;
        int radiusX2 = radiusX * radiusX;
        int radiusY2 = radiusY * radiusY;
        int twoRadiusX2 = 2 * radiusX2;
        int twoRadiusY2 = 2 * radiusY2;
        int px = 0;
        int py = twoRadiusX2 * y;

        putPixel(xc, yc + radiusY, color);

        //Sector 1
        int p = (int) (radiusY2 - radiusX2 * radiusY + 0.25 * radiusX2);
        while (px < py) {
            x++;
            px += twoRadiusY2;
            if (p < 0) {
                p += radiusY2 + px;
            } else {
                y--;
                py -= twoRadiusX2;
                p += radiusY2 + px - py;
            }
            putEllipsePixels(xc, yc, x, y, color);
        }

        //Sector 2
        p = (int) (radiusY2 * (x + 0.5) * (x + 0.5) + radiusX2 * (y - 1) * (y - 1) - radiusX2 * radiusY2);
        while (y > 0) {
            y--;
            py -= twoRadiusX2;
            if (p > 0) {
                p += radiusX2 - py;
            } else {
                x++;
                px += twoRadiusY2;
                p += radiusX2 - py + px;
            }
            putEllipsePixels(xc, yc, x, y, color);
        }

        putPixel(xc, yc - radiusY, color);
    }

    private void putEllipsePixels(int xc, int yc, int x, int y, Color color) {
        putPixel(xc + x, yc + y, color);
        putPixel(xc - x, yc + y, color);
        putPixel(xc + x, yc - y, color);
        putPixel(xc - x, yc - y, color);
    }

    public void drawTriangles(int x0, int y0, int x1, int y1, int x2, int y2, Color color) {
        drawLine(x0, y0, x1, y1, color);
        drawLine(x1, y1, x2, y2, color);
        drawLine(x0, y0, x2, y2, color);
    }

    public void drawDiamonds(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        drawLine(x0, y0, x1, y1, color);
        drawLine(x1, y1, x2, y2, color);
        drawLine(x2, y2, x3, y3, color);
        drawLine(x0, y0, x3, y3, color);
    }

    public void drawParalelaCube() {
        int vp[] = {5, 5, 500};
        int points[][] = {
            {300, 200, 200},
            {300, 400, 300},
            {500, 200, 200},
            {500, 400, 300},
            {200, 300, 200},
            {200, 500, 400},
            {400, 300, 200},
            {400, 500, 400}};

        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-points[i][2]) / vp[2];
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], rojo);
        drawLine(projectedPoints[2][0], projectedPoints[2][1], projectedPoints[6][0], projectedPoints[6][1], rojo);
        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[6][0], projectedPoints[6][1], rojo);

        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[7][0], projectedPoints[7][1], rojo);
        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[1][0], projectedPoints[1][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[3][0], projectedPoints[3][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[3][0], projectedPoints[3][1], rojo);

        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[5][0], projectedPoints[5][1], rojo);
        drawLine(projectedPoints[3][0], projectedPoints[3][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[0][0], projectedPoints[0][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[6][0], projectedPoints[6][1], rojo);

        for (int i = 0; i < 8; i++) {
            int p1x = projectedPoints[i][0];
            int p1y = projectedPoints[i][1];
            putPixel(p1x, p1y, sky);
        }
    }

    public void drawPerspectivaCube() {
        int vp[] = {20, 20, 500};
        int points[][] = {
            {100, 100, 100},
            {100, 300, 100},
            {300, 100, 100},
            {300, 300, 100},
            {100, 100, 300},
            {100, 300, 300},
            {300, 100, 300},
            {300, 300, 300}};

        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-vp[2]) / (points[i][2] - vp[2]);
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

        for (int i = 0; i < 8; i++) {
            int p1x = projectedPoints[i][0];
            int p1y = projectedPoints[i][1];
            putPixel(p1x, p1y, rojo);
        }

        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], rojo);
        drawLine(projectedPoints[2][0], projectedPoints[2][1], projectedPoints[6][0], projectedPoints[6][1], rojo);
        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[6][0], projectedPoints[6][1], rojo);

        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[7][0], projectedPoints[7][1], rojo);
        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[1][0], projectedPoints[1][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[3][0], projectedPoints[3][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[3][0], projectedPoints[3][1], rojo);

        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[5][0], projectedPoints[5][1], rojo);
        drawLine(projectedPoints[3][0], projectedPoints[3][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[0][0], projectedPoints[0][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[6][0], projectedPoints[6][1], rojo);
    }

    public void fill3DCube() {
        int vp[] = {5, 5, 30};
        int points[][] = {
            {300, 200, 200},
            {300, 400, 300},
            {500, 200, 200},
            {500, 400, 300},
            {200, 300, 200},
            {200, 500, 400},
            {400, 300, 200},
            {400, 500, 400}
        };

        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-points[i][2]) / vp[2];
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int i = 0; i < 8; i++) {
            minY = Math.min(minY, projectedPoints[i][1]);
            maxY = Math.max(maxY, projectedPoints[i][1]);
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < 8; i++) {
                int j = (i + 1) % 8;
                int y1 = projectedPoints[i][1];
                int y2 = projectedPoints[j][1];

                if ((y1 <= y && y < y2) || (y2 <= y && y < y1)) {
                    int x = (int) (projectedPoints[i][0] + (y - y1) * 1.0 * (projectedPoints[j][0] - projectedPoints[i][0]) / (y2 - y1));
                    x1 = Math.min(x1, x);
                    x2 = Math.max(x2, x);
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, sky);
                }
            }
        }

        // Llenar las caras restantes
        fillPolygon(sky, projectedPoints[0], projectedPoints[2], projectedPoints[6], projectedPoints[4]); // Cara frontal
        fillPolygon(sky, projectedPoints[1], projectedPoints[3], projectedPoints[7], projectedPoints[5]); // Cara trasera
        fillPolygon(sky, projectedPoints[0], projectedPoints[1], projectedPoints[5], projectedPoints[4]); // Cara izquierda
        fillPolygon(sky, projectedPoints[2], projectedPoints[3], projectedPoints[7], projectedPoints[6]); // Cara derecha
        fillPolygon(sky, projectedPoints[4], projectedPoints[6], projectedPoints[7], projectedPoints[5]); // Cara superior
        fillPolygon(sky, projectedPoints[0], projectedPoints[2], projectedPoints[3], projectedPoints[1]); // Cara inferior
        
        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], rojo);
        drawLine(projectedPoints[2][0], projectedPoints[2][1], projectedPoints[6][0], projectedPoints[6][1], rojo);
        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[6][0], projectedPoints[6][1], rojo);

        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[7][0], projectedPoints[7][1], rojo);
        drawLine(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[1][0], projectedPoints[1][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[3][0], projectedPoints[3][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[3][0], projectedPoints[3][1], rojo);

        drawLine(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[5][0], projectedPoints[5][1], rojo);
        drawLine(projectedPoints[3][0], projectedPoints[3][1], projectedPoints[2][0], projectedPoints[2][1], rojo);
        drawLine(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[0][0], projectedPoints[0][1], rojo);
        drawLine(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[6][0], projectedPoints[6][1], rojo);
    }

    public void fillPolygon(Color color, int[]... vertices) {
        // Encontrar el mínimo y máximo de la coordenada y para cada línea horizontal
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int[] vertex : vertices) {
            minY = Math.min(minY, vertex[1]);
            maxY = Math.max(maxY, vertex[1]);
        }

        // Iterar sobre cada línea horizontal y llenar los píxeles entre los puntos extremos
        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            // Encontrar intersecciones de la línea horizontal con los segmentos del polígono
            for (int i = 0; i < vertices.length; i++) {
                int j = (i + 1) % vertices.length;
                int y1 = vertices[i][1];
                int y2 = vertices[j][1];

                if ((y1 <= y && y < y2) || (y2 <= y && y < y1)) {
                    int x = (int) (vertices[i][0] + (y - y1) * 1.0 * (vertices[j][0] - vertices[i][0]) / (y2 - y1));
                    x1 = Math.min(x1, x);
                    x2 = Math.max(x2, x);
                }
            }

            // Rellenar la línea horizontal entre los puntos extremos
            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, color); // Reemplaza 'putPixel' con el método apropiado para asignar el color
                }
            }
        }
    }

    //Curvas
    public void drawInfinitySymbol(int centerX, int centerY, int size, Color color) {
        double theta = 0;
        double step = 0.01;

        while (theta <= 2 * Math.PI) {
            double x = centerX + size * Math.cos(theta);
            double y = centerY + size * Math.sin(2 * theta) / 2;

            putPixel((int) x, (int) y, color);
            theta += step;
        }
    }

    public void drawWideParabola(int centerX, int centerY, double a, double b, double c, double startX, double endX, double stepSize, Color color) {
        for (double x = startX; x <= endX; x += stepSize) {
            double y = a * x * x + b * x + c;
            putPixel(centerX + (int) x, centerY - (int) y, color);
        }
    }

    public void drawInvertedWideParabola(int centerX, int centerY, double a, double b, double c, double startX, double endX, double stepSize, Color color) {
        for (double x = startX; x <= endX; x += stepSize) {
            double y = -a * x * x - b * x - c;
            putPixel(centerX + (int) x, centerY + (int) y, color);
        }
    }

    public void drawChurro(int t, int tf) {
        int prevPixelX = 0;
        int prevPixelY = 0;
        boolean firstPoint = true;

        while (t < tf) {
            double x = t - 3 * Math.sin(t);
            double y = 4 - 3 * Math.cos(t);

            int pixelX = (int) (x * 10);
            int pixelY = (int) (y * 10);

            pixelY = 100 - pixelY;

            putPixel(pixelX, pixelY, Color.WHITE);

            if (!firstPoint) {
                drawLine(prevPixelX, prevPixelY, pixelX, pixelY, Color.WHITE);
            } else {
                firstPoint = false;
            }

            prevPixelX = pixelX;
            prevPixelY = pixelY;

            t += 1;
        }
    }

    public void drawHumito() {
        for (double y = 0; y <= 2 * Math.PI; y += 0.01) {
            double x = y * Math.cos(4 * y);

            int pixelX = (int) (x * 10) + 100;
            int pixelY = (int) (y * 10) + 100;

            // Invierte pixelY para dibujar de arriba hacia abajo
            pixelY = getHeight() - pixelY;

            putPixel(pixelX, pixelY, ladrillo2);
        }
    }

    public void drawFlor(int points) {
        int numPoints = points;
        double tStep = 2 * Math.PI / numPoints;

        int prevPixelX = 0;
        int prevPixelY = 0;
        boolean firstPoint = true;

        for (int i = 0; i < numPoints; i++) {
            double t = i * tStep;
            double x = Math.cos(t) + (1.0 / 2.0) * Math.cos(7 * t) + (1.0 / 3.0) * Math.sin(17 * t);
            double y = Math.sin(t) + (1.0 / 2.0) * Math.sin(7 * t) + (1.0 / 3.0) * Math.cos(17 * t);

            int pixelX = (int) (x * 20) + 250;
            int pixelY = (int) (y * 20) + 50;

            putPixel(pixelX, pixelY, Color.WHITE);

            if (!firstPoint) {
                drawLine(prevPixelX, prevPixelY, pixelX, pixelY, Color.WHITE);
            } else {
                firstPoint = false;
            }

            prevPixelX = pixelX;
            prevPixelY = pixelY;
        }
    }

    public void drawSol(int points) {
        int numPoints = points;
        double tStep = 14 * Math.PI / numPoints;

        int prevPixelX = 0;
        int prevPixelY = 0;
        boolean firstPoint = true;

        for (int i = 0; i < numPoints; i++) {
            double t = i * tStep;
            double x = 17 * Math.cos(t) + 7 * Math.cos((17.0 / 7.0) * t);
            double y = 17 * Math.sin(t) - 7 * Math.sin((17.0 / 7.0) * t);

            int pixelX = (int) (x * 10) + getWidth() / 2;
            int pixelY = (int) (y * 10) + getHeight() / 2;

            putPixel(pixelX, pixelY, Color.BLUE);

            if (!firstPoint) {
                drawLine(prevPixelX, prevPixelY, pixelX, pixelY, Color.BLUE);
            } else {
                firstPoint = false;
            }

            prevPixelX = pixelX;
            prevPixelY = pixelY;
        }
    }

    //Churro 2D
    public void churro2D() {
        int points = 1000;
        int scale = 100;

        int vp[] = {0, 3, 20};

        float step = (float) (8 * Math.PI / points);

        for (float t = 0; t < 8 * Math.PI; t += step) {
            float u = -t / vp[2];
            float x = (float) (Math.cos(t) + vp[0] * u);
            float y = (float) (Math.sin(t) + vp[1] * u);
            x *= 100;
            y *= 50;
            putPixel((int) x + getWidth() / 2, (int) y + 400, rojo);
        }
    }

    public void churro3D() {
        int points = 1000;
        int scale = 50;

        float vpX = 0;
        float vpY = 3;
        float vpZ = 30;

        float step = (float) (8 * Math.PI / points);

        for (float t = 0; t < 8 * Math.PI; t += step) {
            float u = -t / vpZ;
            float x = (float) (Math.cos(t) + vpX * u);
            float y = (float) (Math.sin(t) + vpY * u);
            float z = (float) (vpZ - t);

            float perspectiveFactor = 1 / (1 + z / scale);
            x *= scale * perspectiveFactor;
            y *= scale * perspectiveFactor;

            int screenX = (int) x + getWidth() / 2;
            int screenY = (int) y + 400;

            putPixel(screenX, screenY, rojo);
        }
    }

    //Mallado
    public void drawMeshFromArrayProduct(int[] array1, int[] array2, int meshSpacing, Color color) {
        int rows1 = array1.length;
        int rows2 = array2.length;

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < rows2; j++) {
                int pixelX = array1[i] * meshSpacing;
                int pixelY = array2[j] * meshSpacing;

                int endX = pixelX + meshSpacing;
                for (int x = pixelX; x < endX; x++) {
                    putPixel(x, pixelY, color);
                }

                int endY = pixelY + meshSpacing;
                for (int y = pixelY; y < endY; y++) {
                    putPixel(pixelX, y, color);
                }
            }
        }
    }

    //Rellenos
    public static void floodFill(int x, int y, int color, int newColor) {
        int rows = matrizPixels.length;
        int cols = matrizPixels[0].length;

        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return;
        }

        if (matrizPixels[x][y] == color) {
            matrizPixels[x][y] = newColor;

            floodFill(x - 1, y, color, newColor); // Izquierda
            floodFill(x + 1, y, color, newColor); // Derecha
            floodFill(x, y - 1, color, newColor); // Arriba
            floodFill(x, y + 1, color, newColor); // Abajo
        }
    }

    public void fillCircle(int x, int y, int radius, Color color) {
        int d = 1 - radius;
        int x1 = 0;
        int y1 = radius;

        while (x1 <= y1) {
            drawLine(x + x1, y + y1, x - x1, y + y1, color);
            drawLine(x + x1, y - y1, x - x1, y - y1, color);
            drawLine(x + y1, y + x1, x - y1, y + x1, color);
            drawLine(x + y1, y - x1, x - y1, y - x1, color);

            if (d < 0) {
                d += 2 * x1 + 3;
            } else {
                d += 2 * (x1 - y1) + 5;
                y1--;
            }
            x1++;
        }
    }

    public void fillRectangle(int x0, int y0, int x1, int y1, Color color) {
        for (int i = y0; i < y1; i++) {
            drawLine(x0, i, x1, i, color);
        }
    }

    public void fillEllipse(int xc, int yc, int radiusX, int radiusY, Color fillColor) {
        int radiusX2 = radiusX * radiusX;
        int radiusY2 = radiusY * radiusY;

        for (int y = yc - radiusY; y <= yc + radiusY; y++) {
            int dy = yc - y;
            int dx = (int) Math.sqrt(radiusX2 - (radiusX2 * dy * dy) / radiusY2);
            drawLine(xc - dx, y, xc + dx, y, fillColor);
        }
    }

    public void fillTriangle(int x0, int y0, int x1, int y1, int x2, int y2, Color fillColor) {
        int width = getWidth();
        int height = getHeight();

        int[] xPoints = {x0, x1, x2};
        int[] yPoints = {y0, y1, y2};

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (pointInTriangle(x, y, xPoints, yPoints)) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    private boolean pointInTriangle(int x, int y, int[] xPoints, int[] yPoints) {
        int x0 = xPoints[0];
        int y0 = yPoints[0];
        int x1 = xPoints[1];
        int y1 = yPoints[1];
        int x2 = xPoints[2];
        int y2 = yPoints[2];

        double d0 = sign(x, y, x0, y0, x1, y1);
        double d1 = sign(x, y, x1, y1, x2, y2);
        double d2 = sign(x, y, x2, y2, x0, y0);

        boolean hasNeg = (d0 < 0) || (d1 < 0) || (d2 < 0);
        boolean hasPos = (d0 > 0) || (d1 > 0) || (d2 > 0);

        return !(hasNeg && hasPos);
    }

    private double sign(int x, int y, int x0, int y0, int x1, int y1) {
        return (x - x1) * (y0 - y1) - (x0 - x1) * (y - y1);
    }

    public void fillDiamond(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3, Color fillColor) {
        int minX = Math.min(x0, Math.min(x1, Math.min(x2, x3)));
        int minY = Math.min(y0, Math.min(y1, Math.min(y2, y3)));
        int maxX = Math.max(x0, Math.max(x1, Math.max(x2, x3)));
        int maxY = Math.max(y0, Math.max(y1, Math.max(y2, y3)));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (pointInDiamond(x, y, x0, y0, x1, y1, x2, y2, x3, y3)) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    private boolean pointInDiamond(int x, int y, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
        int d0 = (x - x0) * (y1 - y0) - (x1 - x0) * (y - y0);
        int d1 = (x - x1) * (y2 - y1) - (x2 - x1) * (y - y1);
        int d2 = (x - x2) * (y3 - y2) - (x3 - x2) * (y - y2);
        int d3 = (x - x3) * (y0 - y3) - (x0 - x3) * (y - y3);

        return (d0 >= 0 && d1 >= 0 && d2 >= 0 && d3 >= 0) || (d0 <= 0 && d1 <= 0 && d2 <= 0 && d3 <= 0);
    }

    //Transformaciones
    public void translate(int x, int y) {
        if (bufferedImage != null) {
            BufferedImage bufferedTranslate = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedTranslate.getGraphics();
            graphics.drawImage(this.bufferedImage, x, y, null);
            graphics.dispose();
            this.bufferedImage = bufferedTranslate;
            repaint();
        }
    }

    public void scale(double scaleX, double scaleY) {
        if (bufferedImage != null) {
            int width = getWidth();
            int height = getHeight();
            int newWidth = (int) (width * scaleX);
            int newHeight = (int) (height * scaleY);

            BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < newWidth; x++) {
                for (int y = 0; y < newHeight; y++) {
                    int originalX = (int) (x / scaleX);
                    int originalY = (int) (y / scaleY);

                    if (originalX >= 0 && originalX < width && originalY >= 0 && originalY < height) {
                        int pixelColor = bufferedImage.getRGB(originalX, originalY);
                        scaledImage.setRGB(x, y, pixelColor);
                    }
                }
            }

            bufferedImage = scaledImage;
            repaint();
        }
    }

    public void rotate(double angle) {
        if (bufferedImage != null) {
            int width = getWidth();
            int height = getHeight();

            BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            double centerX = width / 2.0;
            double centerY = height / 2.0;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    double xOffset = x - centerX;
                    double yOffset = y - centerY;

                    double newX = xOffset * Math.cos(angle) - yOffset * Math.sin(angle) + centerX;
                    double newY = xOffset * Math.sin(angle) + yOffset * Math.cos(angle) + centerY;

                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        int pixelColor = bufferedImage.getRGB(x, y);
                        rotatedImage.setRGB((int) newX, (int) newY, pixelColor);
                    }
                }
            }

            bufferedImage = rotatedImage;
            repaint();
        }
    }

    public void clear() {
        bufferedImage = null;
        repaint();
    }

    public void setBackgroundColor(Color color) {
        BufferedImage bufferedImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImg.getGraphics();
        graphics.setColor(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.dispose();
        this.bufferedImage = bufferedImg;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
