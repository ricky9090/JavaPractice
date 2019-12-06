package study.mystream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * 模仿Stream API，研究Stream的执行流程
 * MyStream执行的打印顺序应与Java Stream一致
 */
public class MyStream<T> implements Up {

    final List<T> sourceList = new ArrayList<>();

    Function op = null;
    MyStream next = null;

    Up up = null;
    boolean noMore = false;

    public void onEventOver(boolean over) {
        this.noMore = over;
    }

    @Override
    public void trigger() {
        if (up != null) {
            // 触发上层数据源
            up.trigger();
        }

        // 捕获上层传递数据
        T item = capture();

        // 检查后续是否还有数据，提前通知下层
        if (up == null) {
            // 最顶层数据源
            if (sourceList.size() > 0) {
                // 有数据
                noMore = false;
                if (next != null) {
                    next.onEventOver(this.noMore);
                }
            } else {
                // 没有数据了，提示下层不再trigger
                noMore = true;
                if (next != null) {
                    next.onEventOver(this.noMore);
                }
            }
        }

        // 通过转换，将数据递给下层
        drop(item);
    }

    private T capture() {
        if (sourceList.size() > 0) {
            T source = sourceList.get(0);
            sourceList.remove(0);
            return source;
        }
        return null;
    }

    private void drop(T source) {
        if (op != null && source != null) {
            next.sourceList.clear();
            next.sourceList.add(op.apply(source));
        }
        next.onEventOver(this.noMore);
    }

    public <R> MyStream<R> map(Function<T, R> mapper) {
        this.op = mapper;
        MyStream<R> nextStream = new MyStream<>();
        nextStream.up = this;
        this.next = nextStream;
        return nextStream;
    }

    /*public <R> MyStream<R> flatMap(Function<T, MyStream<R>> flatMapper) {
        // TODO
        return new MyStream<>();
    }*/

    public T reduce(BinaryOperator<T> binaryOperator) {
        List<T> reduceList = new ArrayList<>();
        while (!noMore) {
            if (up != null) {
                up.trigger();
            }

            if (sourceList.size() > 0) {
                reduceList.add(sourceList.get(0));
                sourceList.clear();
            }

            if (reduceList.size() == 2) {
                T reduceResult = binaryOperator.apply(reduceList.get(0), reduceList.get(1));
                reduceList.clear();
                reduceList.add(reduceResult);
            }
        }

        if (reduceList.size() > 0) {
            return reduceList.get(0);
        }
        return null;
    }

    public static <S> MyStream<S> of(List<S> input) {
        MyStream<S> stream = new MyStream<>();
        stream.sourceList.addAll(input);
        return stream;
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("red"); //3
        test.add("blue"); //4
        test.add("green"); //5

        Optional<Integer> result = test.stream().map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("map String uppercase: " + s);
                return s.toUpperCase();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(result.get());



        MyStream<String> testStream = MyStream.of(test);

        Integer testResult = testStream.map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("map String uppercase: " + s);
                return s.toUpperCase();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(testResult);
    }
}
