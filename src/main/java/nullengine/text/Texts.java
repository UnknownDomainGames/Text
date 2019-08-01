package nullengine.text;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Texts implements Collection<Text> {

    private List<Text> list;

    private Texts(List<Text> texts) {
        this.list = texts;
    }

    public String getOriginalText() {
        return list
                .stream()
                .map(Text::getText)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public String serialize() {
        return list
                .stream()
                .map(Text::serialize)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public static Texts as(Text... texts) {
        return Texts.as(Arrays.asList(texts));
    }

    public static Texts as(List<Text> texts) {
        return new Texts(texts);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public boolean add(Text o) {
        return list.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray(a);
    }

    public Text get(int index){
        return list.get(index);
    }
}
