package com.ctbc.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.ctbc.model.vo.PicVO;
import com.ctbc.util.HibernateUtil;

public class PicDao {

	public static void save() throws IOException {
		PicVO picVO = new PicVO();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		File file = new File("E:\\IMG_0003.JPG");
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		Blob picture = Hibernate.getLobCreator(session).createBlob(bytesArray);
		picVO.setPicture(picture);
		picVO.setName("IMG_0003.JPG");

		session.save(picVO);
		session.getTransaction().commit();
		fis.close();
	}

	public static void write() throws SQLException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		PicVO picVO = session.get(PicVO.class, 1);
		Blob picture = picVO.getPicture();
		InputStream inputStream = picture.getBinaryStream();
		FileOutputStream fos = new FileOutputStream("F:\\" + picVO.getName());
		byte[] buff = new byte[1024];
		int len = 0;

		while ((len = inputStream.read(buff)) != -1) {
			fos.write(buff, 0, len);
		}

		fos.close();
		inputStream.close();
		session.getTransaction().commit();
	}

	public static void main(String[] args) throws IOException, SQLException {
		save();
		write();
		HibernateUtil.getSessionFactory().close();
	}

}
