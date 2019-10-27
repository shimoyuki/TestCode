package com.cardinal;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class DataDAO {
	private Patent patent = new Patent();
	private Intelligence intel = new Intelligence();
	private Literature litera = new Literature();

	public void save(Object obj){
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			System.out.println("数据保存失败.");
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	private <T> List<T> getById(T t, int id){
		List<T> li = null;
		try {
			Session session = HibernateUtil.getSession();
			String hql = null;
			if(t.getClass().equals(Patent.class)){
				hql = "from Patent patent where patent.pId="+id;
			}else if(t.getClass().equals(Intelligence.class)){
				hql = "from Intelligence intel where intel.iId="+id;
			}else if(t.getClass().equals(Literature.class)){
				hql = "from Literature litera wherelitera.lId="+id;
			}
			Query q = session.createQuery(hql);
			li = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
		return li;
	}
	private <T> List<T> getByExpertName(T t, String name){
		List<T> li = null;
		try {
			Session session = HibernateUtil.getSession();
			String hql = null;
			if(t.getClass().equals(Patent.class)){
				hql = "from Patent patent where patent.pExpertName='"+name+"'";
			}else if(t.getClass().equals(Intelligence.class)){
				hql = "from Intelligence intel where intel.iExpertName='"+name+"'";
			}else if(t.getClass().equals(Literature.class)){
				hql = "from Literature litera where litera.lExpertName='"+name+"'";
			}
			Query q = session.createQuery(hql);
			li = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
		return li;
	}
	private <T> List<T> getByTitle(T t, String title){
		List<T> li = null;
		try {
			Session session = HibernateUtil.getSession();
			String hql = null;
			if(t.getClass().equals(Patent.class)){
				hql = "from Patent patent where patent.pTitle='"+title+"'";
			}else if(t.getClass().equals(Intelligence.class)){
				hql = "from Intelligence intel where intel.iTitle='"+title+"'";
			}else if(t.getClass().equals(Literature.class)){
				hql = "from Literature litera where litera.lTitle='"+title+"'";
			}
			Query q = session.createQuery(hql);
			li = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
		return li;
	}

	public List<Patent> getPatentById(int id){
		return this.getById(new Patent(), id);
	}
	public List<Patent> getPatentByExpertName(String name){
		return this.getByExpertName(new Patent(), name);
	}
	public List<Patent> getPatentByTitle(String title){
		return this.getByTitle(new Patent(), title);
	}
	public List<Intelligence> getIntelligenceById(int id){
		return this.getById(new Intelligence(), id);
	}
	public List<Intelligence> getIntelligenceByExpertName(String name){
		return this.getByExpertName(new Intelligence(), name);
	}
	public List<Intelligence> getIntelligenceByTitle(String title){
		return this.getByTitle(new Intelligence(), title);
	}
	public List<Literature> getLiteratureById(int id){
		return this.getById(new Literature(), id);
	}
	public List<Literature> getLiteratureByExpertName(String name){
		return this.getByExpertName(new Literature(), name);
	}
	public List<Literature> getLiteratureByTitle(String title){
		return this.getByTitle(new Literature(), title);
	}
	
	private void delById(String content, int id){
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			if(content.equals("p")){
				patent = (Patent) session.get(Patent.class, id);
				session.delete(patent);
			}else if(content.equals("i")){
				intel = (Intelligence) session.get(Intelligence.class, id);
				session.delete(intel);
			}else if(content.equals("l")){
				litera= (Literature) session.get(Literature.class, id);
				session.delete(litera);
			}
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	public void delPatentById(int id){
		this.delById("p", id);
	}
	public void delPatentByExpertName(String name){
		List<Patent> lp = this.getPatentByExpertName(name);
		Iterator<Patent> ip = lp.iterator();
		while(ip.hasNext()){
			this.delById("p", ip.next().getpId());
		}
	}
	public void delPatentByTitle(String title){
		List<Patent> lp = this.getPatentByTitle(title);
		Iterator<Patent> ip = lp.iterator();
		while(ip.hasNext()){
			this.delById("p", ip.next().getpId());
		}
	}
	public void delIntelligenceById(int id){
		this.delById("i", id);
	}
	public void delIntelligenceByExpertName(String name){
		List<Intelligence> li = this.getIntelligenceByExpertName(name);
		Iterator<Intelligence> ii = li.iterator();
		while(ii.hasNext()){
			this.delById("i", ii.next().getiId());
		}
	}
	public void delIntelligenceByTitle(String title){
		List<Intelligence> li = this.getIntelligenceByTitle(title);
		Iterator<Intelligence> ii = li.iterator();
		while(ii.hasNext()){
			this.delById("i", ii.next().getiId());
		}
	}
	public void delLiteratureById(int id){
		this.delById("l", id);
	}
	public void delLiteratureByExpertName(String name){
		List<Literature> ll = this.getLiteratureByExpertName(name);
		Iterator<Literature> il = ll.iterator();
		while(il.hasNext()){
			this.delById("l", il.next().getlId());
		}
	}
	public void delLiteratureByTitle(String title){
		List<Literature> ll = this.getLiteratureByTitle(title);
		Iterator<Literature> il = ll.iterator();
		while(il.hasNext()){
			this.delById("l", il.next().getlId());
		}
	}	
	
	public  <T> void updateById(T t, int id){
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			if(t instanceof Patent){
				Patent p = (Patent)t;
				patent = (Patent) session.get(Patent.class, id);
				patent.setPatent(p);
			}else if(t instanceof Intelligence){
				Intelligence i = (Intelligence)t;
				intel = (Intelligence) session.get(Intelligence.class, id);
				intel.setIntelligence(i);
			}else if(t instanceof Literature){
				Literature l = (Literature)t;
				litera= (Literature) session.get(Literature.class, id);
				litera.setLiterature(l);
			}
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	private String strFilter(String str){
		str = str.replace('\"', ',');
		str = str.replace(',', ' ');
		str = str.trim();
		return str;
	}

	private void setPatent(String str1, String str2){
		switch(this.strFilter(str1)){
		case "title":
			patent.setpTitle(str2);
			break;
		case "date":
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			try {
				Date d = sdf.parse(str2);

				patent.setpDate(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case "abs":
			patent.setpAbs(str2);
			break;
		case "applicant":
			patent.setpApplicant(str2);
			break;
		case "inventor":
			patent.setpInventor(str2);
			break;
		case "expert_name":
			patent.setpExpertName(str2);
			break;
		case "expert_org":
			patent.setpExpertOrg(str2);
			break;
		default:
			System.out.println("数据格式有误");
		}
	}

	private void setInteligence(String str1, String str2){
		switch(str1){
		case "title":
			intel.setiTitle(str2);
			break;
		case "abs":
			intel.setiAbs(str2);
			break;
		case "date":
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date d = sdf.parse(str2);
				intel.setiDate(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case "url":
			intel.setiUrl(str2);
			break;
		case "expert_name":
			intel.setiExpertName(str2);
			break;
		case "expert_org":
			intel.setiExpertOrg(str2);
			break;
		default:
			System.out.println("数据格式有误");
		}
	}

	private void setLiterature(String str1, String str2){
		switch(str1){
		case "title":
			litera.setlTitle(str2);
			break;
		case "abs":
			litera.setlAbs(str2);
			break;
		case "author_cn":
			litera.setlAuthorCn(str2);
			break;
		case "unit":
			litera.setlUnit(str2);
			break;
		case "journal_cn":
			litera.setlJournalCn(str2);
			break;
		case "app_date":
			litera.setlAppDate(str2);
			break;
		case "key":
			litera.setlKey(str2);
			break;
		case "expert_name":
			litera.setlExpertName(str2);
			break;
		case "expert_org":
			litera.setlExpertOrg(str2);
			break;
		default:
			System.out.println("数据格式有误");
		}
	}

	public void trans(String path, String content){
		//File data =  new File(path);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
		MessageForm mf = new MessageForm(content);
		BufferedReader br = null;
		String str = "";
		boolean flag = true;
		boolean correct = true;
		long count = 0;
		try {
			/*br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(data),"UTF-8"));*/
			br = new BufferedReader(
					new InputStreamReader(is,"UTF-8"));
			while(flag){
				try {
					str = br.readLine();
					if((str.equals("},") || str.equals("}"))&&correct){
						if(content.equals("p")){
							this.save(patent);
						}else if(content.equals("i")){
							this.save(intel);
						}else if(content.equals("l")){
							this.save(litera);
						}
						count++;
						if(count%200 == 0){
							mf.setText("已录入"+count+"条数据。");
						}
					}else if(str.equals("{")){
						correct = true;
					}else{
						String[] strs = str.split(" : ");
						if(strs.length==1){
							correct = false;
						}else{
							if(content.equals("p")){
								this.setPatent(this.strFilter(strs[0]), this.strFilter(strs[1]));
							}else if(content.equals("i")){
								this.setInteligence(this.strFilter(strs[0]), this.strFilter(strs[1]));
							}else if(content.equals("l")){
								this.setLiterature(this.strFilter(strs[0]), this.strFilter(strs[1]));
							}
						}
					}
				} catch (NullPointerException e) {
					flag = false;
					//System.out.println("存储完毕.");
					//System.out.println("共录入"+count+"条数据");
					mf.setText("共录入"+count+"条数据");
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		//new DataDAO().trans("data/专利.txt", "p");
		//new DataDAO().trans("data/情报.txt", "i");
		//new DataDAO().trans("data/文献.txt", "l");
		/**数据读入*/
		/**保存测试
		Patent p = new Patent("ti", new Date(), "ab", "ap", "in", "en", "eo", "pa");
		DataDAO dd = new DataDAO();
		dd.save(p);
		*/
		/**查询测试
		DataDAO dd = new DataDAO();
		List<Patent> lp = null;
		lp = dd.getPatentById(700);
		lp = dd.getPatentByExpertName("en");
		lp = dd.getPatentByTitle("ti");
		Iterator<Patent> ip = lp.iterator();
		while(ip.hasNext()){
			System.out.println("Abs: " + ip.next().getpAbs());
		}
		*/
		/**删除测试
		DataDAO dd = new DataDAO();
		dd.delPatentByTitle("ti");
		*/
		/**更新测试
		DataDAO dd = new DataDAO();
		dd.updateById(new Patent("ti", new Date(), "ab", "ap", "in", "en", "eo", "pa"), 702);
		*/
	}
}

@SuppressWarnings("serial")
class MessageForm extends JFrame{
	JTextField jtf = new JTextField("开始录入。");
	
	public String getText() {
		return jtf.getText();
	}

	public void setText(String str) {
		jtf.setText(str);
	}

	public MessageForm() throws HeadlessException {
		super();
	}

	public MessageForm(String title) throws HeadlessException {
		if(title.equals("p")){
			this.setTitle("专利录入中……");
		}else if(title.equals("i")){
			this.setTitle("情报录入中……");
		}else if(title.equals("l")){
			this.setTitle("文献录入中……");
		}
		this.setBackground(Color.CYAN);
		this.setBounds(500, 100, 500, 300);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		jtf.setAutoscrolls(true);
		jtf.setEditable(false);
		jtf.setHorizontalAlignment(JTextField.CENTER);
		c.add(BorderLayout.CENTER, jtf);
		this.setContentPane(c);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
