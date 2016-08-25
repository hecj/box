package com.demo.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.peon.core.file.FileUtil;
import cn.peon.core.kit.template.FreemarkerTempleteUtil;
import cn.peon.core.log.LOG;

import com.demo.common.BaseController;
import com.demo.common.interceptor.UserUtil;
import com.demo.service.dbs.model.Dbs;
import com.demo.service.q.model.Q;
import com.demo.service.qh.model.Qh;
import com.demo.service.qo.model.Qo;
import com.demo.service.r.model.R;
import com.demo.service.u.model.U;
import com.demo.service.util.DBSUTIL;
import com.demo.util.DateUtil;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
/**
 * 信息查询
 */
public class QueryController extends BaseController {
	
	public void getStaticUrl() throws FileNotFoundException, IOException {
		
		Long id = getParaToLong("id");
		Qh qh = Qh.dao.findById(id);
		Long qid = qh.getLong("qid");
		U u = UserUtil.getU(getSession());
		R r = R.dao.findFirst("select * from r where id=?",u.get("rid"));
		
		String qs = r.get("qs"); 
		qs=","+qs+",";
		
		String wp=getWebPath();
		String pf = new File(wp).getParent();
		File file=new File(pf,qh.getStr("url"));
		if (!file.exists()) {
			setAttr("msg", "执行结果未找到，或未执行完成，如有疑问，请联系技术支持！！");
			renderJson();
		}else {
			if (qs.contains(","+qid+",")) {
				String jsonText = FileUtil.readFile(file, "UTF-8");
				renderJson(jsonText);
			}else {
				setAttr("msg", "您无权查询该统计内容！！");
				renderJson();
			}
		}
		
		
//		setAttr("qol",qol);
		
	}
	public void index() {
		
		U u = UserUtil.getU(getSession());
		R r = R.dao.findFirst("select * from r where id=?",u.get("rid"));
		
		String qs = r.get("qs");
		List<Q> ql = Q.dao.find("select * from q where type=0 and stat=0 and id in("+qs+") order by clc desc");
		List<Qo> qol = Qo.dao.find("select * from qo where qid in ("+qs+")");
		
		setAttr("ql",ql);
//		setAttr("qol",qol);
		
		render("index.ftl");
	}
	public void statics() {
		
		U u = UserUtil.getU(getSession());
		R r = R.dao.findFirst("select * from r where id=?",u.get("rid"));
		
		String qs = r.get("qs");
		List<Q> ql = Q.dao.find("select * from q where type=1 and stat=0 and id in("+qs+") order by clc desc");
		List<Qo> qol = Qo.dao.find("select * from qo where qid in ("+qs+")");
		
		setAttr("ql",ql);
//		setAttr("qol",qol);
		
		render("statics.ftl");
	}
	/**
	 * 统计查询
	 */
	public void staticex() {
		Date dt = new Date();
		Long id = getParaToLong("id");
		
		
		
		
		String sx = getPara("sx");
		Q q = Q.dao.findById(id);
		setAttr("q", "q");
		Long qhid=null;
		ArrayList<String> whereps = new ArrayList<String>();
		if (q!=null) {
			String sql = q.get("sql");
			List<String> sql_fs = Arrays.asList( DBSUTIL.getFields(sql).split(","));
			setAttr("sql_fs", sql_fs);
			Long dbid = q.getLong("dbid");
			if (dbid==null||dbid<=0) {
				setAttr("msg", "当前查询配置的数据源不存在!");
			}else {
				Dbs dbs = Dbs.dao.findById(dbid);
				if (dbs==null) {
					setAttr("msg", "当前查询配置的数据源不存在!");
				}else {
					Config c = DBSUTIL.getDb(dbs);
					if(c!=null) {
						String[] sxz = sx.split(",");
						
						for (String key : sxz) {
							whereps.add(getPara("where."+key));
						}
						
						String pms=JsonKit.toJson(whereps);//参数
						
						int i = q.getInt("ms");
						if (i<=0) {//不限制
							
						}else{
							
							List<Qh> ql = Qh.dao.find("select * from qh where qid=? and pms=? and dt > FROM_UNIXTIME("+(System.currentTimeMillis()/1000-i*60)+") ",id,pms);
							
							if(ql.size()>0){
								setAttr("msg", i+"分钟内已有用户执行过相同的统计,查询常见者限定统计时间不得小于"+i+"分钟请查看他的查询历史!");
								renderJson();
								return ;
							}
							
						}
						
						
						{//先记录执行历史
							
							Integer clc = q.getInt("clc");
							if (clc==null) {
								clc=0;
							}
							try{
								q.set("clc", clc+1);
								q.update();
								
								Qh qh = new Qh();
								qh.set("qid", id);
								qh.set("dt", dt);
								
								qh.set("pms", pms);
								qh.set("t", 1);
								qh.save();
								qhid=qh.getLong("id");
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							
						}
						
						String url = null ;
						
						List<Record> q_rl = null;
						try {
							List<Qo> qol = Qo.dao.find("select * from qo where qid=?",id);
							setAttr("qol",qol);
							q_rl = onlyEx(whereps, sql, dbs,id);
							url = rendHtmlAndEx(q_rl,id,sql_fs,qol);
							System.out.println("shengchengurl:"+url);
						} catch (ParseException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try{
							Qh qh = Qh.dao.findById(qhid);
							qh.set("edt", new Date());
							qh.set("url", url);
							qh.update();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
//						Object q_rl = DBSUTIL.invokeMeth("query",Db.use(DBSUTIL.COMMON+dbs.getLong("id")), sql, whereps);
						setAttr("q_rl", q_rl);
					}else {
						setAttr("msg", "数据源配置出错!");
						LOG.info(dbid+"数据源配置出错!");
					}
				}
			}
		}else {
			setAttr("msg", "当前查询的对象不存在!");
		}
		
		
		
		
		
		renderJson();
//		render("ex_res.ftl");
	}
	/**生成静态统计文件
	 * @param whereps
	 * @param sql
	 * @param dbs
	 * @param id 
	 * @return
	 * @throws ParseException 
	 */
	private List<Record> onlyEx(ArrayList<String> whereps, String sql,
			Dbs dbs, Long id)  {
		//执行最终查询结果  限制最多查100条 
		List<Record> q_rl = Db.use(DBSUTIL.COMMON+dbs.getLong("id")).find(sql+" limit 0,100",whereps.toArray());
		
		return q_rl;
	}
	/**生成静态统计文件
	 * @param whereps
	 * @param sql
	 * @param dbs
	 * @param id 
	 * @param qol 
	 * @param sql_fs 
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	private String rendHtmlAndEx(List<Record> q_rl, Long id, List<String> sql_fs, List<Qo> qol) throws ParseException, IOException {
		//执行最终查询结果  限制最多查100条 
		
		Map<String, Object> content=new HashMap<String, Object>();
		
		
		
		
		content.put("sql_fs", sql_fs);
		content.put("qol", qol);
		content.put("q_rl", q_rl);
		String json = JsonKit.toJson(content);
		
		String wp=getWebPath();
		String jsonpath = "querystatic/genstatics/";
		String filep=jsonpath+DateUtil.currDate("yyyy年MM月dd日HH时mm分ss秒SSS")+"查询"+id+".json";
		String pf = new File(wp).getParent();
		File f=new File(pf,filep);
		FileUtil.createFile(f);
		f.createNewFile();
		FileUtil.writeTo(json, f, "UTF-8");
		
		return filep;
	}
	private String getWebPath() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}
		java.net.URL url = classLoader.getResource("");
		String ROOT_CLASS_PATH = url.getPath()+"/";
		File rootFile = new File(ROOT_CLASS_PATH);
		String WEB_INFO_DIRECTORY_PATH = rootFile.getParent()+"/";
		File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);
		String SERVLET_CONTEXT_PATH = webInfoDir.getParent()+"/";
		return SERVLET_CONTEXT_PATH;

	}
	/**
	 * 通用查询
	 */
	public void ex() {
		Date dt = new Date();
		Long id = getParaToLong("id");
		String sx = getPara("sx");
		Q q = Q.dao.findById(id);
		setAttr("q", "q");
		String pms="[]";
		ArrayList<String> whereps = new ArrayList<String>();
		if (q!=null) {
			String sql = q.get("sql");
			setAttr("sql_fs", Arrays.asList( DBSUTIL.getFields(sql).split(",")));
			Long dbid = q.getLong("dbid");
			if (dbid==null||dbid<=0) {
				setAttr("msg", "当前查询配置的数据源不存在!");
			}else {
				Dbs dbs = Dbs.dao.findById(dbid);
				if (dbs==null) {
					setAttr("msg", "当前查询配置的数据源不存在!");
				}else {
					Config c = DBSUTIL.getDb(dbs);
					if(c!=null) {
						String[] sxz = sx.split(",");
						
						for (String key : sxz) {
							whereps.add(getPara("where."+key));
						}
						pms=JsonKit.toJson(whereps);
						
						int i = q.getInt("ms");
						if (i<=0) {//不限制
							
						}else{
							
							List<Qh> ql = Qh.dao.find("select * from qh where qid=? and pms=? and dt > FROM_UNIXTIME("+(System.currentTimeMillis()/1000-i*60)+") ",id,pms);
							
							if(ql.size()>0){
								setAttr("msg", i+"分钟内已有用户执行过相同的查询,查询创建者限制查询间隔不得小于"+i+"分钟!");
								renderJson();
								return ;
							}
							
						}
						
						
						List<Record> q_rl = onlyEx(whereps, sql, dbs,id);
//						Object q_rl = DBSUTIL.invokeMeth("query",Db.use(DBSUTIL.COMMON+dbs.getLong("id")), sql, whereps);
						setAttr("q_rl", q_rl);
					}else {
						setAttr("msg", "数据源配置出错!");
						LOG.info(dbid+"数据源配置出错!");
					}
				}
			}
		}else {
			setAttr("msg", "当前查询的对象不存在!");
		}
		
		List<Qo> qol = Qo.dao.find("select * from qo where qid=?",id);
		setAttr("qol",qol);
		
		Integer clc = q.getInt("clc");
		if (clc==null) {
			clc=0;
		}
		try{
			q.set("clc", clc+1);
			q.update();
			
			Qh qh = new Qh();
			qh.set("qid", id);
			qh.set("dt", dt);
			qh.set("pms", pms);
			qh.set("t", 0);
			qh.set("edt", new Date());
			
			Map hm=new HashMap<String, String>();
			hm.put("q_rl", getAttr("q_rl"));
			hm.put("qol", qol);
			hm.put("sql_fs",getAttr("sql_fs") );
			String rs=JsonKit.toJson(hm);
			if (rs.length()<=4000) {
				qh.set("url",rs);
			}else {
				qh.set("url", "内容超过4000不予存储");
			}
			qh.save();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		renderJson();
//		render("ex_res.ftl");
	}
	
	public void his() {
		Long id = getParaToLong("qid");
		setAttr("qid", id);
		Page<Qh> qhl = Qh.dao.paginate(getParaToInt("page",1), 10, "select *", "from qh where qid=? order by id desc",id);
		setAttr("pg",qhl);
		render("his.ftl");
	}
	
	
	public void prewhere() {
		Long id = getParaToLong("id");
		List<Qo> qol = Qo.dao.find("select * from qo where qid=?",id);
		setAttr("qol",qol);
		renderJson();
	}
//	
//	public void add() {
//		List<R> rl = R.dao.find("select * from r order by id desc");
//		setAttr("rl", rl);
//	}
//	
//	@Before(UValidator.class)
//	public void save() {
//		getModel(U.class).save();
//		redirect("/u");
//	}
//	
//	public void edit() {
//		setAttr("u", U.dao.findById(getParaToInt()));
//		List<R> rl = R.dao.find("select * from r order by id desc");
//		setAttr("rl", rl);
//	}
//	
//	@Before(UValidator.class)
//	public void update() {
//		getModel(U.class).update();
//		redirect("/u");
//	}
//	
//	public void delete() {
//		U.dao.deleteById(getParaToInt());
//		redirect("/u");
//	}
//	
//	public void deleteByIds() {
//		U.dao.deleteByIds(getParaValues("id"));
//		redirect("/u");
//	}
	@Override
	public String getMn() {
		return "信息查询";
	}
}


