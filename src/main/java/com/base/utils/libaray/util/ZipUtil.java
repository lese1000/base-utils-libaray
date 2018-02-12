package com.base.utils.libaray.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
public class ZipUtil {
    private static byte[] _byte = new byte[1024] ;
    /**
     * 压缩文件或路径
     * @param path 压缩的目的地址
     * @param srcFiles 压缩的源文件
     */
    public static void zipFile( String path , List<File> srcFiles ){
        try {
            if( path.endsWith(".zip") || path.endsWith(".ZIP") ){
                ZipOutputStream _zipOut = new ZipOutputStream(new FileOutputStream(new File(path))) ;
                _zipOut.setEncoding("GBK");
                for( File _f : srcFiles ){
                    handlerFile(path , _zipOut , _f , "");
                }
                _zipOut.close();
            }else{
                System.out.println("target file[" + path + "] is not .zip type file");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
    
    /**
     * 
     * @param zip 压缩的目的地址
     * @param zipOut 
     * @param srcFile  被压缩的文件信息
     * @param path  在zip中的相对路径
     * @throws IOException
     */
    private static void handlerFile(String zip , ZipOutputStream zipOut , File srcFile , String path ) throws IOException{
        System.out.println(" begin to compression file[" + srcFile.getName() + "]");
        if( !"".equals(path) && ! path.endsWith(File.separator)){
            path += File.separator ;
        }
        if( ! srcFile.getPath().equals(zip) ){
            if( srcFile.isDirectory() ){
                File[] _files = srcFile.listFiles() ;
                if( _files.length == 0 ){
                    zipOut.putNextEntry(new ZipEntry( path + srcFile.getName() + File.separator));
                    zipOut.closeEntry();
                }else{
                    for( File _f : _files ){
                        handlerFile( zip ,zipOut , _f , path + srcFile.getName() );
                    }
                }
            }else{
                InputStream _in = new FileInputStream(srcFile) ;
                zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                int len = 0 ; 
                while( (len = _in.read(_byte)) > 0  ){
                    zipOut.write(_byte, 0, len);
                }
                _in.close();
                zipOut.closeEntry();
            }
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     * @param zipName 待解压缩的ZIP文件名
     * @param targetBaseDirName  目标目录
     */
    public static List<File> upzipFile(String zipPath, String descDir) {
        return upzipFile( new File(zipPath) , descDir ) ;
    }
    
    /**
     * 对.zip文件进行解压缩
     * @param zipFile  解压缩文件
     * @param descDir  压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
     * @return
     */
    @SuppressWarnings("rawtypes")
   public static List<File> upzipFile(File zipFile, String descDir) {
       List<File> _list = new ArrayList<File>() ;
       try {
           ZipFile _zipFile = new ZipFile(zipFile , "GBK") ;
           for( Enumeration entries = _zipFile.getEntries() ; entries.hasMoreElements() ; ){
               ZipEntry entry = (ZipEntry)entries.nextElement() ;
               File _file = new File(descDir + File.separator + entry.getName()) ;
               if( entry.isDirectory() ){
                   _file.mkdirs() ;
               }else{
                   File _parent = _file.getParentFile() ;
                   if( !_parent.exists() ){
                       _parent.mkdirs() ;
                   }
                   InputStream _in = _zipFile.getInputStream(entry);
                   OutputStream _out = new FileOutputStream(_file) ;
                   int len = 0 ;
                   while( (len = _in.read(_byte)) > 0){
                       _out.write(_byte, 0, len);
                   }
                   _in.close(); 
                   _out.flush();
                   _out.close();
                   _list.add(_file) ;
               }
           }
       } catch (IOException e) {
       }
       return _list ;
   }
   
   /**
    * 对临时生成的文件夹和文件夹下的文件进行删除
    */
   public static void deletefile(String delpath) {
       try {
           File file = new File(delpath);
           if (!file.isDirectory()) {
               file.delete();
           } else if (file.isDirectory()) {
               String[] filelist = file.list();
               for (int i = 0; i < filelist.length; i++) {
                   File delfile = new File(delpath + File.separator + filelist[i]);
                   if (!delfile.isDirectory()) {
                       delfile.delete();
                   } else if (delfile.isDirectory()) {
                       deletefile(delpath + File.separator + filelist[i]);
                   }
               }
               file.delete();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public static void main(String[] args) {}
   
   
   /**
   *方法描述：<b>将多个文件压缩成zip包</b></br>
   */
   public static ByteArrayOutputStream fileToZip(List<File> filedata,String zipName,String tempFilePath){
	   byte[] buffer = new byte[1024];
	   ZipOutputStream out=null;
	   try{
		   out = new ZipOutputStream(new FileOutputStream(tempFilePath+zipName+".zip"));
		   for(int j=0,len=filedata.size();j<len;j++){
			   FileInputStream fis=new FileInputStream(filedata.get(j));
			   out.putNextEntry(new ZipEntry(filedata.get(j).getName()));
			   int dataLen;
			   //读入需要下载的文件的内容，打包到zip文件
			   while((dataLen=fis.read(buffer))>0){
			   out.write(buffer,0,dataLen);
			   }
			   out.closeEntry();
			   fis.close();
		   }
		   out.close();
		   }catch(Exception ex){
		   ex.printStackTrace();
		   }
		   //读取压缩包
		   File filezip=new File(tempFilePath+zipName+".zip");
		   ByteArrayOutputStream baos=null;
		   try{
			   baos = new ByteArrayOutputStream();
			   FileInputStream inStream=new FileInputStream(filezip);
			   BufferedInputStream bis=new BufferedInputStream(inStream);
			   int c =bis.read();
			   while(c!=-1){
			   baos.write(c);
			   c=bis.read();
			   }
			   bis.close();
			   inStream.close();
		   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	   return baos;
   }
   
}

