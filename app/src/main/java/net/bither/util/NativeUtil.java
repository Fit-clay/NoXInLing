package net.bither.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.File;

/**
 * ,.
 * ,_> `.   ,';
 * ,-`'      `'   '`'._
 * ,,-) ---._   |   .---''`-),.
 * ,'      `.  \  ;  /   _,'     `,
 * ,--' ____       \   '  ,'    ___  `-,
 * _>   /--. `-.              .-'.--\   \__
 * '-,  (    `.  `.,`~ \~'-. ,' ,'    )    _\
 * _<    \     \ ,'  ') )   `. /     /    <,.
 * ,-'   _,  \    ,'    ( /      `.    /        `-,
 * `-.,-'     `.,'       `         `.,'  `\    ,-'
 * ,'       _  /   ,,,      ,,,     \     `-. `-._
 * /-,     ,'  ;   ' _ \    / _ `     ; `.     `(`-\
 * /-,        ;    (o)      (o)      ;          `'`,
 * ,~-'  ,-'    \     '        `      /     \      <_
 * /-. ,'        \                   /       \     ,-'
 * '`,     ,'   `-/             \-' `.      `-. <
 * /_    /      /   (_     _)   \    \          `,
 * `-._;  ,' |  .::.`-.-' :..  |       `-.    _\
 * _/       \  `:: ,^. :.:' / `.        \,-'
 * '`.   ,-'  /`-..-'-.-`-..-'\            `-.
 * >_ /     ;  (\/( ' )\/)  ;     `-.    _<
 * ,-'      `.  \`-^^^-'/  ,'        \ _<
 * `-,  ,'   `. `"""""' ,'   `-.   <`'
 * ')        `._.,,_.'        \ ,-'
 * '._        '`'`'   \       <
 * >   ,'       ,   `-.   <`'
 * `,/          \      ,-`
 * `,   ,' |   /     /
 * '; /   ;        (
 * _)|   `       (
 * `')         .-'
 * <_   \   /    Created by Eric on 2017/1/6.
 * \   /\(
 * `;/  `
 */

public class NativeUtil {

    private static int DEFAULT_QUALITY = 95;

    public static void compressBitmap(Bitmap bit, String fileName,boolean optimize) {
        compressBitmap(bit, DEFAULT_QUALITY, fileName, optimize);
    }

    public static void compressBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        Log.d("native", "compress of native");
        File file = new File(fileName),parent = file.getParentFile();
        if(!parent.exists()){ parent.mkdirs(); }

        if (bit.getConfig() != Bitmap.Config.ARGB_8888) {
            Bitmap result = null;
            result = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            Rect rect = new Rect(0, 0, bit.getWidth(), bit.getHeight());
            canvas.drawBitmap(bit, null, rect, null);
            saveBitmap(result, quality, fileName, optimize);
            result.recycle();
            System.gc();
        } else {
            saveBitmap(bit, quality, fileName, optimize);
        }

    }

    private static void saveBitmap(Bitmap bit, int quality, String fileName,boolean optimize) {
        try{
            compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality,fileName.getBytes(), optimize);
        }catch (Exception e){}
    }

    private static native String compressBitmap(Bitmap bit, int w, int h,int quality, byte[] fileNameBytes, boolean optimize);

    static {
        try{
            System.loadLibrary("jpegbither");
            System.loadLibrary("bitherjni");
        }catch (Exception e){}
    }

}
