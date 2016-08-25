package cn.peon.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;

import cn.peon.core.kit.StringUtil;

public class ClassUtil {
	/**
	 * 空的参数列表
	 */
	public final static Object[] NILARGS=new Object[0];;

	/**
	 * 判断一个类是否是抽象的
	 * 
	 * @param cls
	 * @return
	 */
	public static boolean isAbstractClass(Class<?> cls) {
		int mf = cls.getModifiers();
		return (!Modifier.isFinal(mf)) && (Modifier.isAbstract(mf));
	}

	/**
	 * 获取属性描述中数组等容器内数据类型
	 * 
	 * @param descriptor
	 * @return
	 */
	public static Type getArrayActualType(PropertyDescriptor descriptor) {
		Method mt = descriptor.getWriteMethod();
		Type[] tps = mt.getGenericParameterTypes();
		if (tps.length > 0) {

			ParameterizedType ptt = (ParameterizedType) (tps[0]);
			return ptt.getActualTypeArguments()[0];
			// el=getPrimitiveValue(ct, el);//尝试获取基础类型
			// co.add(el);
		} else {
			return null;
		}
	}

	/**
	 * 获取类中的PropertyDescriptors
	 * 
	 * @param type
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> type)
			throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		return propertyDescriptors;
	}

	/**
	 * 根据名称获取propertyDescriptor
	 * 
	 * @param key1
	 * @param propertyDescriptors
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static PropertyDescriptor getTypeByName(String key1,
			PropertyDescriptor[] propertyDescriptors)
			throws InstantiationException, IllegalAccessException {
		if (key1 == null || "".equals(key1)) {
			return null;
		} else {
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (key1.equals(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					return descriptor;
				}
			}
			return null;
		}
	}

	/**
	 * 是否是一个基础类型的class
	 * 
	 * @param cls
	 * @param si
	 * @return
	 */
	public static boolean isPrimitiveClass(Class<?> cls) {
		if (cls == int.class || cls == Integer.class||
				cls == float.class || cls == Float.class||
				cls == double.class || cls == Double.class||
				cls == byte.class || cls == Byte.class||
				cls == char.class || cls == Character.class||
				cls == boolean.class || cls == Boolean.class||
				cls == long.class || cls == Long.class||
				cls == short.class || cls == Short.class||
				cls == String.class)//多算上一个string 类
		{
			return true;
		}
		return false;
	}
	/**
	 * 获取一个基础数据类型的数组
	 * 
	 * @param cls
	 * @param si
	 * @return
	 */
	public static Object initPrimitiveArray(Class<?> cls, int si) {
		if (cls == int.class || cls == Integer.class) {
			return (cls == int.class) ? new int[si] : new Integer[si];
		} else if (cls == float.class || cls == Float.class) {
			return (cls == float.class) ? new float[si] : new Float[si];
		} else if (cls == double.class || cls == Double.class) {
			return (cls == double.class) ? new double[si] : new Double[si];
		} else if (cls == byte.class || cls == Byte.class) {
			return (cls == byte.class) ? new byte[si] : new Byte[si];
		} else if (cls == char.class || cls == Character.class) {
			return (cls == char.class) ? new char[si] : new Character[si];
		} else if (cls == boolean.class || cls == Boolean.class) {
			return (cls == boolean.class) ? new boolean[si] : new Boolean[si];
		} else if (cls == long.class || cls == Long.class) {
			return (cls == long.class) ? new long[si] : new Long[si];
		} else if (cls == short.class || cls == Short.class) {
			return (cls == short.class) ? new short[si] : new Short[si];
		} else {
			return new Object[si];
		}
	}

	/**
	 * 生成数组
	 * 
	 * @param value
	 * @param args
	 * @param propertyName
	 * @param ty
	 * @param si
	 * @return 
	 */
	public static Object genArray(Object value,  
			String propertyName, Class<?> ty, int si) {
		Class<?> ct = ty.getComponentType();// 容器内类型
		// System.out.println(value.getClass()+"  pp");
		Object valueproxy = ClassUtil.initPrimitiveArray(ct, si);

		for (int j = 0; j < si; j++) {
			Object el = Array.get(value, j);
			// boolean bs = ct.isPrimitive();//如果是基础类型
			// if (bs) {
			el = getPrimitiveValue(ct, el);// 尝试获取基础类型
			try {
				Array.set(valueproxy, j, el);
			} catch (IllegalArgumentException e) {
				System.out.println("为数组写入内容时候报错:(" + propertyName + ")" + j
						+ ">>" + el);
			}
			// }
		}

		// Class<?> carray = Class.forName(new ct[]{}.getClass().getName());
		return valueproxy;// carray.cast(valueproxy);
		// System.out.println(propertyName+"---"+valueproxy);

		// System.out.println(ty);
	}

	/**
	 * 获取集合数据类型数据
	 * 
	 * @param value
	 * @param descriptor
	 * @param args
	 * @param ty
	 * @param si
	 * @return 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Collection<Object> genCollection(Object value,
			PropertyDescriptor descriptor,  Class<?> ty, int si)
			throws InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		Collection<Object> co = (Collection<Object>) (ty.newInstance());
		for (int j = 0; j < si; j++) {
			Object el = Array.get(value, j);
			// boolean bs = ct.isPrimitive();//如果是基础类型
			// if (bs) {
			Type atp = ClassUtil.getArrayActualType(descriptor);
			el = getPrimitiveValue((Class<?>) atp, el);
			co.add(el);
			// System.out.println(atp);
			// }
		}

		return co;
		// System.out.println(propertyName);
		// System.out.println(ty);
	}

	/**
	 * 获取基础数据类型的数据
	 * 
	 * @param cls
	 * @param value
	 * @return
	 */
	public static Object getPrimitiveValue(Class<?> cls, Object value) {
		if (value == null) {
			return null;
		}
		
		try {
			if(cls == String.class || cls == String.class) {
				return getString(value);
			} else if (cls == int.class || cls == Integer.class) {
				return getInteger(value);
			} else if (cls == float.class || cls == Float.class) {
				return getFloat(value);
			} else if (cls == double.class || cls == Double.class) {
				return getDouble(value);
			} else if (cls == byte.class || cls == Byte.class) {
				return getByte(value);
			} else if (cls == char.class || cls == Character.class) {
				return getChar(value);
			} else if (cls == boolean.class || cls == Boolean.class) {
				return getBoolean(value);
			} else if (cls == long.class || cls == Long.class) {
				return getLong(value);
			} else if (cls == short.class || cls == Short.class) {
				return getShort(value);
			} else if (cls == Date.class || cls == Date.class) {
				return getDate(value);
			} else {
				return cls.cast(value);
			}
		} catch (Exception e) {
			System.out.println("基本数据类型转换异常");
		}
		return value;

	}
	@SuppressWarnings("deprecation")
	public static Date getDate(Object value) {
		if (null==value) {
			return  null;
		}
		if (value.getClass()==Date.class) {
			return (Date)value;
		}
		String strv = getString(value);
		return new Date(strv);
	}
	public static Short getShort(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Short.parseShort(strv);
	}

	public static Long getLong(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Long.parseLong(strv);
	}

	public static Boolean getBoolean(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Boolean.parseBoolean(strv);
	}

	public static Character getChar(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return strv.length() > 0 ? strv.charAt(0) : null;
	}

	public static Byte getByte(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Byte.parseByte(strv);
	}

	public static Double getDouble(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Double.parseDouble(strv);
	}

	public static Float getFloat(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Float.parseFloat(strv);
	}

	public static Integer getInteger(Object value) {
		if (null==value) {
			return  null;
		}
		String strv = getString(value);
		return Integer.parseInt(strv);
	}
	public static String getString(Object value) {
		if (null==value) {
			return null;
		}
		String strv = value.toString();
		return strv;
	}

	/**获取所有字段
	 * @param class1
	 * @return
	 */
	public static Field[] getFileds(Class<? extends Object> class1) {
		Field[] rs=null==class1?null:class1.getDeclaredFields();
		if (null!=class1) {
			Class<?> sp = class1.getSuperclass();
			Field[] spfs = getFileds(sp);
			if (null!=spfs&&spfs.length>0) {
//				HashSet<Field> fs = new HashSet<Field>();
//				fs.addAll(Arrays.asList(rs));
//				fs.addAll(Arrays.asList(spfs));
				rs= concat(rs, spfs);
			}
		}
		return rs;
	}
	static Field[] concat(Field[] rs, Field[] spfs) {
		   Field[] c= new Field[rs.length+spfs.length];
		   System.arraycopy(rs, 0, c, 0, rs.length);
		   System.arraycopy(spfs, 0, c, rs.length, spfs.length);
		   return c;
	}
	/**从对象中获取数据 根据field
	 * @param f
	 * @param o
	 * @return
	 */
	public static Object getValueByField(Field f,Object o) {
		String name = f.getName();
		return getValueByFieldName( name,o);
	}
	/**从对象中获取数据 根据field name
	 * @param name
	 * @param o
	 * @return
	 */
	public static Object getValueByFieldName( String name,Object o) {
		if (o!=null) {
		    PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(name,o.getClass());
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		    Method getMethod = pd.getReadMethod();//获得get方法
	       	try {
				return getMethod.invoke(o);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**获取容器内泛型对象
	 * @return
	 */
	public static Class<?> getGenericsType(Class<?> cls) {
		Type genType = cls.getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<?>) params[0];
	}

	public static void invoke(Object obj, String methodname/*, Object[] args, Class<?> paramtype*/) {
		if ((null!=obj)&&(!StringUtil.isNullOrEmpty(methodname))) {
			try {
				Method m = obj.getClass().getMethod(methodname, new Class[0]);
				m.invoke(obj, NILARGS);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}
	

	
}
