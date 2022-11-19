package com.tourbest.erp.util;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	/**
	 * 객체의 로딩을 지원하는 유틸 클래스
	 * <p>
	 * <b>NOTE:</b> Class 로딩 등의 객체관련 기능을 제공하는 유틸이다.
	 * @author 실행환경 개발팀 윤성종
	 * @since 2009.06.01
	 * @version 1.0
	 * @see <pre>
	 *  == 개정이력(Modification Information) ==
	 *
	 *   수정일      수정자           수정내용
	 *  -------    --------    ---------------------------
	 *   2009.06.01  윤성종           최초 생성
	 *
	 * </pre>
	 */
	public class ObjectUtil {

		private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtil.class);

	    private ObjectUtil() {

	    }

	    /**
	     * 클래스명으로 객체를 로딩한다.
	     * @param className
	     * @return
	     * @throws ClassNotFoundException
	     * @throws Exception
	     */
		public static Class<?> loadClass(String className) throws ClassNotFoundException, Exception {

			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);;

			if (clazz == null) {
				clazz = Class.forName(className);
			}

			return clazz;
		}

	    /**
	     * 클래스명으로 객체를 로드한 후 인스턴스화 한다.
	     * @param className
	     * @return
	     * @throws ClassNotFoundException
	     * @throws InstantiationException
	     * @throws IllegalAccessException
	     * @throws Exception
	     */
		public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
			Class<?> clazz;

			try {
				clazz = loadClass(className);
				return clazz.newInstance();
			} catch (ClassNotFoundException e) {
				LOGGER.error("{} : Class is can not instantialized.", className);
				throw new ClassNotFoundException();
			} catch (InstantiationException e) {
				LOGGER.error("{} : Class is can not instantialized.", className);
				throw new InstantiationException();
			} catch (IllegalAccessException e) {
				LOGGER.error("{} : Class is not accessed.", className);
				throw new IllegalAccessException();
			} catch (Exception e) {
				LOGGER.error("{} : Class is not accessed.", className);
				throw new Exception(e);
			}
		}

	    /**
	     * 클래스명으로 파라매터가 있는 클래스의 생성자를 인스턴스화 한다.
	     * 예) Class <?> clazz = EgovObjectUtil.loadClass(this.mapClass);
	     * Constructor <?> constructor = clazz.getConstructor(new Class[]{DataSource.class, String.class});
	     * Object [] params = new Object [] {
	     *     getDataSource(), getUsersByUsernameQuery()
	     *  };
	     * this.usersByUsernameMapping = (EgovUsersByUsernameMapping)constructor.newInstance(params);
	     * @param className
	     * @return
	     * @throws ClassNotFoundException
	     * @throws InstantiationException
	     * @throws IllegalAccessException
	     * @throws Exception
	     */
		public static Object instantiate(String className, String[] types, Object[] values) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
			Class<?> clazz;
			Class<?>[] classParams = new Class[values.length];
			Object[] objectParams = new Object[values.length];

			try {
				clazz = loadClass(className);

				for (int i = 0; i < values.length; i++) {
					classParams[i] = loadClass(types[i]);
					objectParams[i] = values[i];
				}

				Constructor<?> constructor = clazz.getConstructor(classParams);
				return constructor.newInstance(values);

			} catch (ClassNotFoundException e) {
				LOGGER.error("{} : Class is can not instantialized.", className);
				throw new ClassNotFoundException();
			} catch (InstantiationException e) {
				LOGGER.error("{} : Class is can not instantialized.", className);
				throw new InstantiationException();
			} catch (IllegalAccessException e) {
				LOGGER.error("{} : Class is not accessed.", className);
				throw new IllegalAccessException();
			} catch (Exception e) {
				LOGGER.error("{} : Class is not accessed.", className);
				throw new Exception(e);
			}
		}

	    /**
	     * 객체가 Null 인지 확인한다.
	     * @param object
	     * @return Null인경우 true / Null이 아닌경우 false
	     */
	    public static boolean isNull(Object object) {
	        return ((object == null) || object.equals(null));
	    }
	}
