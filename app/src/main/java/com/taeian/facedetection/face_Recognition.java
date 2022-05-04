package com.taeian.facedetection;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class face_Recognition {

    private Interpreter interpreter;
    private int INPUt_SIZE;
    private int height=0;
    private int width=0;

    private GpuDelegate gpuDelegate=null;

    private CascadeClassifier cascadeClassifier;

    face_Recognition(AssetManager assetManager, Context context,String modelPath,int input_size) throws IOException{
        INPUt_SIZE=input_size;
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        //options.addDelegate(gpuDelegate);
        options.setNumThreads(8);
        interpreter=new Interpreter(loadModel(assetManager,modelPath),options);
        Log.d("face_Recognition","Model yüklendi");
        try{
            //haar cascade tanımlama
            InputStream inputStream=context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            File cascadeDir=context.getDir("cascade",Context.MODE_PRIVATE);
            File mCascadeFile=new File(cascadeDir,"haarcascade_frontalface_alt");
            FileOutputStream outputStream=new FileOutputStream(mCascadeFile);
            //boş buffer oluşturuyoruz
            byte[] buffer=new byte[4096];
            int byteRead;
            //data bitince -1
            while ((byteRead=inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,byteRead);
            }
            //okuma bitince
            inputStream.close();
            outputStream.close();
            //save file yeri
            cascadeClassifier=new CascadeClassifier(mCascadeFile.getAbsolutePath());
            Log.d("face_recognition","Classifier Yüklendi");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public Mat recognizeImage(Mat mat_image){

        //onCameraFrame çağırıyor CameraActivityden
        Core.flip(mat_image.t(),mat_image,1);
        Mat grayscaleImage=new Mat();
        Imgproc.cvtColor(mat_image,grayscaleImage,Imgproc.COLOR_RGBA2GRAY);
        height=grayscaleImage.height();
        width=grayscaleImage.width();
        int absoluteFaceSize=(int)(height*0.1);
        MatOfRect faces=new MatOfRect();

        if(cascadeClassifier != null){
            cascadeClassifier.detectMultiScale(grayscaleImage,faces,1.1,2,2,new Size(absoluteFaceSize,absoluteFaceSize),new Size());

        }

        Rect[] faceArray=faces.toArray();
        for(int i=0;i<faceArray.length;i++){
            //                input/output                  bitiş noktası      Renk    R  G  B  Alpha
            Imgproc.rectangle(mat_image,faceArray[i].tl(),faceArray[i].br(),new Scalar(0,255,0,255),2);
            Rect roi=new Rect((int)faceArray[i].tl().x,(int)faceArray[i].tl().y,((int)faceArray[i].br().x)-((int)faceArray[i].tl().x),((int)faceArray[i].br().y)-((int)faceArray[i].tl().y));

            Mat cropped_rgb=new Mat(mat_image,roi);
            Bitmap bitmap=null;
            bitmap=Bitmap.createBitmap(cropped_rgb.cols(),cropped_rgb.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cropped_rgb,bitmap);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,INPUt_SIZE,INPUt_SIZE,false);
            ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap);

            float[][] face_value=new float[1][1];
            interpreter.run(byteBuffer,face_value);
            Log.d("face_Recognition","Out:  "+ Array.get(Array.get(face_value,0),0));
            float read_face=(float) Array.get(Array.get(face_value,0),0);
            String face_name=get_face_name(read_face);
            Imgproc.putText(mat_image,""+face_name+""+read_face,new Point((int)faceArray[i].tl().x+10,(int)faceArray[i].tl().y+20),1,1.5,new Scalar(255,0,0,150),2);

        }


        Core.flip(mat_image.t(),mat_image,0);
        return mat_image;
    }


    private String get_face_name(float read_face){
        String val= "";
        if(read_face>=0 & read_face<0.5){
            val="Sıfır";
        }
        else if (read_face>=0.5 & read_face<1.5){
            val="Arnold Schwarzenegger";
        }
        else if (read_face>=1.5 & read_face<2.5){
            val="David Schwimmer";
        }
        else if (read_face>=2.5 & read_face<3.5){
            val="mat leblanc";
        }
        else if (read_face>=3.5 & read_face<4.5){
            val="Muhammet";
        }
        else if (read_face>=4.5 & read_face<5.5){
            val="simon helberg";
        }
        else if (read_face>=5.5 & read_face<6.5){
            val="scarlett johansson";
        }
        else if (read_face>=6.5 & read_face<7.5){
            val="matthew perry";
        }
        else if (read_face>=7.5 & read_face<8.5){
            val="Gokhan";
        }
        else if (read_face>=8.5 & read_face<9.5){
            val="sylvester stallone";
        }
        else if (read_face>=9.5 & read_face<10.5){
            val="messi";
        }
        else if (read_face>=10.5 & read_face<11.5){
            val="Courtney Cox";
        }
        else if (read_face>=12.5 & read_face<13.5){
            val="lisa kudrow";
        }
        else if (read_face>=13.5 & read_face<14.5){
            val="Brad pitt";
        }
        else if (read_face>=15.5 & read_face<16.5){
            val="ronaldo";
        }
        else if (read_face>=16.5 & read_face<17.5){
            val="Angelina Jolie";
        }
        else if (read_face>=18.5 & read_face<19.5){
            val="OnAltı";
        }
        else if (read_face>=19.5 & read_face<20.5){
            val="OnYedi";
        }
        else if (read_face>=20.5 & read_face<21.5){
            val="OnSekiz";
        }
        else if(read_face>22){
            val="Çok büyük";
        }



        return val;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;
        int input_size=INPUt_SIZE;
        byteBuffer=ByteBuffer.allocateDirect(4*1*input_size*input_size*3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[input_size*input_size];
        scaledBitmap.getPixels(intValues,0,scaledBitmap.getWidth(),0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight());
        int pixels=0;

        for(int i=0;i<input_size;++i){
            for(int j=0;j<input_size;++j){
                final int val=intValues[pixels++];
                byteBuffer.putFloat((((val>>16)&0xFF))/255.0f);
                byteBuffer.putFloat((((val>>8)&0xFF))/255.0f);
                byteBuffer.putFloat(((val & 0xFF))/255.0f);
            }
        }
        return byteBuffer;
    }

    //model.tflite yükleniyor
    private MappedByteBuffer loadModel(AssetManager assetManager,String modelPath) throws IOException{
        AssetFileDescriptor assetFileDescriptor=assetManager.openFd(modelPath);
        FileInputStream inputStream=new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=assetFileDescriptor.getStartOffset();
        long declaredLength=assetFileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

}
