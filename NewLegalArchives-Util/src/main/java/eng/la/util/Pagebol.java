/*
 * @author s.mariniello 10/2009
 */

package eng.la.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Pagebol<E>
extends HashMap<String, List<E>> {
    private int sizeMap = 0;
    private int numberPage = 1;
    private int size = 0;
    private int pageCurrent = 1;

    public Pagebol(List<E> lts, int limit) {
        this.startPage();
        this.createPage(lts, limit);
    }

    public Pagebol(Iterator<E> it, int limit) {
        this.startPage();
        this.createPage(it, limit);
    }

    public Pagebol(HashMap<?, E> maps, int limit) {
        this.startPage();
        this.createPage(maps, limit);
    }

    public Pagebol() {
    }

    private void startPage() {
        this.numberPage = 1;
        this.size = 0;
        this.sizeMap = 0;
    }

    public HashMap<String, List<E>> getPages() {
        return this;
    }

    public List<E> getPages(int page) {
        this.setPageCurrent(page);
        return this.get(String.valueOf(page)) != null ? (List<E>)this.get(String.valueOf(page)) : new ArrayList();
    }

    public Iterator<E> getPagesIt(int page) {
        this.setPageCurrent(page);
        return ((List<E>)this.get(String.valueOf(page))).iterator() != null ? ((List<E>)this.get(String.valueOf(page))).iterator() : new ArrayList<E>().iterator();
    }

    public int getTotalNumberPage() {
        return this.numberPage;
    }

    public int getPageEnd() {
        return this.numberPage + 1;
    }

    public int getPageStart() {
        return 1;
    }

    public int getPageCurrent() {
        return this.pageCurrent;
    }

    private void setPageCurrent(int page) {
        this.pageCurrent = page;
    }

    public void createPage(HashMap<?, E> maps, int limit) {
    	   if(maps==null){
        	   maps=new HashMap<String, E>();
           	}
    	int map = maps.keySet().size();
        List<E> contenuto = new ArrayList<E>();
        if (map > (limit * this.numberPage)) {
            int i;
            for (i = this.sizeMap; i < (limit * this.numberPage); ++i) {
                contenuto.add(maps.get(maps.keySet().toArray()[i]));
            }
            this.size++;
            this.sizeMap = (limit * this.size);
            this.put("" + this.numberPage + "", contenuto);
            if (map > (limit * this.numberPage + limit)) {
                this.numberPage++;
                this.createPage(maps, limit);
            } else {
                this.numberPage++;
                contenuto = new ArrayList<E>();
                for (i = this.sizeMap; i < map; ++i) {
                    contenuto.add(maps.get(maps.keySet().toArray()[i]));
                }
                this.put("" + this.numberPage + "", contenuto);
            }
        } else {
            if (this.numberPage > 1) {
                ++this.numberPage;
            }
            contenuto = new ArrayList<E>();
            for (int i = 0; i < map; ++i) {
                contenuto.add(maps.get(maps.keySet().toArray()[i]));
            }
            this.put("" + this.numberPage + "", contenuto);
        }
    }

    public void createPage(Iterator<E> it, int limit) {
        List<E> lts = this.transformIterator(it);
        this.createPage(lts, limit);
    }

    public void createPage(List<E> maps, int limit) {
       if(maps==null){
    	   maps=new ArrayList<E>();
       	}
    	int map = maps.size();
        List<E> contenuto = new ArrayList<E>();
        if (map > limit * this.numberPage) {
            int i;
            E e;
            for (i = this.sizeMap; i < limit * this.numberPage; ++i) {
                e = maps.get(i);
                contenuto.add(e);
            }
            ++this.size;
            this.sizeMap = limit * this.size;
            this.put("" + this.numberPage + "", contenuto);
            if (map > limit * this.numberPage + limit) {
                ++this.numberPage;
                this.createPage(maps, limit);
            } else {
                ++this.numberPage;
                contenuto = new ArrayList<E>();
                for (i = this.sizeMap; i < map; ++i) {
                    e = maps.get(i);
                    contenuto.add(e);
                }
                this.put("" + this.numberPage + "", contenuto);
            }
        } else {
            if (this.numberPage > 1) {
                ++this.numberPage;
            }
            contenuto = new ArrayList<E>();
            for (int i = 0; i < map; ++i) {
                E e = maps.get(i);
                contenuto.add(e);
            }
            this.put("" + this.numberPage + "", contenuto);
        }
    }

    private List<E> transformIterator(Iterator<E> it) {
        List<E> lt = new ArrayList<E>();
        if(it!=null){
        if (it.hasNext()) {
            while (it.hasNext()) {
                lt.add(it.next());
            }
        }
        }
        return lt;
    }
}

