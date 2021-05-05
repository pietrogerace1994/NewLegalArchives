package eng.la.business;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
 

public abstract class BaseService<D, V> {

	public List<V> convertiVoInView(List<D> listaVo) throws Throwable {
		if( listaVo != null ){
			List<V> listaRitorno = new ArrayList<V>();
			for( D vo : listaVo ){
				V view = convertiVoInView(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}

	public V convertiVoInView(D vo) throws Throwable {
		V view = leggiClassView().newInstance();
		Method metodoSetVo = leggiClassView().getMethod("setVo", leggiClassVO());
		metodoSetVo.invoke(view, vo);  
		return view;		
	}
	
	protected abstract Class<D> leggiClassVO();
	
	protected abstract Class<V> leggiClassView();
	
	public List<V> convertiVoInView(List<D> listaVo, Class<D> voClass, Class<V> viewClass) throws Throwable {
		if( listaVo != null ){
			List<V> listaRitorno = new ArrayList<V>();
			for( D vo : listaVo ){
				V view = convertiVoInView(vo, voClass, viewClass);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}

	public V convertiVoInView(D vo, Class<D> voClass, Class<V> viewClass) throws Throwable {
		V view = viewClass.newInstance();
		Method metodoSetVo = viewClass.getMethod("setVo", voClass);
		metodoSetVo.invoke(view, vo);  
		return view;		
	}
}
