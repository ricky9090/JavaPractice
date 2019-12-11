package study.mystream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 另一种思路
 * 下游触发上层调用，由上层通过onNext回调向下游发送数据。
 */
public class RxNode<T> implements RxSource {

    private final List<T> sourceList = new ArrayList<>();

    private Function op = null;

    // 上游数据源
    private RxSource source = null;

    // 下游
    private RxNode downNode = null;

    // 消费回调
    private Consumer<T> consumer = null;

    @Override
    public void subscribe(RxNode down) {
        this.downNode = down;
        if (source != null && sourceList.size() == 0) {
            source.subscribe(this);
            // 上层执行后直接返回
            return;
        }

        for (T data : sourceList) {
            Object result = op.apply(data);
            if (result instanceof RxNode) {
                downNode.onNext(((RxNode) result).sourceList);
            } else {
                downNode.onNext(result);
            }
        }
    }

    /**
     * 向下游发送一个数据
     * @param data
     */
    public void onNext(T data) {
        if (this.consumer != null) {
            consumer.accept(data);
            return;
        }

        if (this.downNode != null) {
            Object result = op.apply(data);
            if (result instanceof RxNode) {
                downNode.onNext(((RxNode) result).sourceList);
            } else {
                downNode.onNext(result);
            }
        }
    }

    /**
     * FlatMap后，向下游批量发送数据
     * @param data
     */
    public void onNext(List<T> data) {
        this.sourceList.clear();
        this.sourceList.addAll(data);
        for (T t : sourceList) {
            Object result = op.apply(t);
            downNode.onNext(result);
        }
    }

    public RxNode(RxSource source) {
        this.source = source;
    }

    public <R> RxNode<R> map(Function<T, R> mapper) {
        this.op = mapper;
        RxNode<R> down = new RxNode<>(this);
        return down;
    }

    public <R> RxNode<R> flatMap(Function<T, RxNode<R>> flatMapper) {
        this.op = flatMapper;
        RxNode<R> down = new RxNode<>(this);
        return down;
    }

    /**
     * 在本层设置Consumer，并触发上层
     * @param consumer
     */
    public void subscribe(Consumer<T> consumer) {
        this.consumer = consumer;
        if (source != null) {
            source.subscribe(this);
        }
        // 由上层主动下发，此处不做处理
    }

    public static <S> RxNode<S> of(List<S> input) {
        RxNode<S> stream = new RxNode<>(null);
        stream.sourceList.addAll(input);
        return stream;
    }

    public static <S> RxNode<S> of(S[] input) {
        RxNode<S> stream = new RxNode<>(null);
        Collections.addAll(stream.sourceList, input);
        return stream;
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("red"); //3
        test.add("blue"); //4
        test.add("green"); //5

        Observable.fromIterable(test).map(new io.reactivex.functions.Function<String, String[]>() {
            @Override
            public String[] apply(String s) throws Exception {
                System.out.println("map String to array: " + s);
                return s.split("");
            }
        }).flatMap(new io.reactivex.functions.Function<String[], ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String[] strings) throws Exception {
                System.out.print("flatmap String[] to stream: ");
                printArray(strings);
                return Observable.fromArray(strings);
            }
        }).map(new io.reactivex.functions.Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).subscribe(new io.reactivex.functions.Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

        System.out.println();
        System.out.println("------------");
        System.out.println();

        RxNode<String> testStream = RxNode.of(test);

        testStream.map(new Function<String, String[]>() {
            @Override
            public String[] apply(String s) {
                System.out.println("map String to array: " + s);
                return s.split("");
            }
        }).flatMap(new Function<String[], RxNode<String>>() {
            @Override
            public RxNode<String> apply(String[] strings) {
                System.out.print("flatmap String[] to stream: ");
                printArray(strings);
                return RxNode.of(strings);
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
    }

    public static void printArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            return;
        }
        for (int i = 0; i < strings.length; i++) {
            System.out.print(strings[i] + ", ");
        }
        System.out.println();
    }
}
