package com.thx.xuitest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class CaptchaUtil {
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static CaptchaUtil Instance;
    private static CaptchaUtil getInstance(){
        if(Instance == null){
            Instance = new CaptchaUtil();
        }
        return Instance;
    }
    private final Random random = new Random();

    private static final int default_code_length = 4;
    private static final int default_font_size = 55;
    private static final int default_line_number = 5;
    private static final int base_padding_left = 20, range_padding_left = 30, base_padding_top = 40, range_padding_top = 20;
    private static final int default_width = 200, default_height = 80;
    private final int width = default_width, height = default_height;
    private int basePaddingLeft = base_padding_left, rangePaddingLeft = range_padding_left,
            basePaddingTop = base_padding_top, rangePaddingTop = range_padding_top;
    private int codeLength = default_code_length, lineNumber = default_line_number;
    private int fontSize = default_font_size;
    private String code;
    private int padding_left, padding_top;
    private final Paint paint = new Paint();
    private Bitmap createBitmap(){
        padding_left = 0;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        code = createCode();

        canvas.drawColor(Color.WHITE);
        paint.setTextSize(fontSize);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle();
            randomPadding();
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        for (int i = 0; i < lineNumber; i++) {
            drawLine(canvas);
        }
        canvas.save();
        canvas.restore();
        return bitmap;
    }
    public String getCode(){
        return code;
    }
    public Bitmap getBitmap(){
        return createBitmap();
    }
    private String createCode(){
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
    private void drawLine(Canvas canvas){
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#CCCCCC"));
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
    private void randomTextStyle(){
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());
        float skewX = (float) random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX);
        paint.setUnderlineText(true);
    }
    private void randomPadding(){
        padding_left += basePaddingLeft + random.nextInt(rangePaddingLeft);
        padding_top = basePaddingTop + random.nextInt(rangePaddingTop);
    }
    private int randomColor(){
        return randomColor(1);
    }
    private int randomColor(int rate){
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }
}
