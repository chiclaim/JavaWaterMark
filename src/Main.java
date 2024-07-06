import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void addTextWaterMark(BufferedImage targetImg, Color textColor, int fontSize, String text, String outPath) {
        try {
            int width = targetImg.getWidth(); //图片宽
            int height = targetImg.getHeight(); //图片高

            if (text.length() * fontSize > width) {
                fontSize = width / text.length() - 5;
            }

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(targetImg, 0, 0, width, height, null);
            g.setColor(textColor); //水印颜色
            g.setFont(new Font("微软雅黑", Font.ITALIC, fontSize));
            int horizontalGap = 20;
            int verticalGap = 40;
            int x = horizontalGap;
            int y = fontSize + verticalGap;
//            g.rotate(-Math.toRadians(20));


            for(;;){
                AffineTransform at = new AffineTransform();
                at.rotate(-Math.toRadians(20), x, y);
                g.setTransform(at);
                g.drawString(text, x, y);
                // 水平方向
                if (x + text.length() * fontSize < width) {
                    x = x + horizontalGap + text.length() * fontSize;
                }
                else {
                    if (y + verticalGap + fontSize > height) break;
                    y = y + verticalGap + fontSize;
                    x = horizontalGap;
                }
            }
            FileOutputStream outImgStream = new FileOutputStream(outPath);
            ImageIO.write(bufferedImage, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/apple/Downloads/sfz.png");
        BufferedImage image = ImageIO.read(file);
        addTextWaterMark(image, new Color(128, 128, 128, 130), 20, "仅用于实名认证，他用无效", "/Users/apple/Downloads/WX20240706-103043-waterMark.jpg");
    }
}