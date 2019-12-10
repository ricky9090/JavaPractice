package study.mystream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * 模仿Stream API，研究Stream的执行流程<p>
 * MyStream执行的打印顺序应与Java Stream一致
 *
 * <h2>思路</h2>
 * 将Stream想象成从楼梯顶层往下的水流<p>
 * 链式调用是从顶向下构建每一节台阶的过程，每个操作符是每层台阶的转换过程。<p>
 * 当调用reduce方法后，每层仅在没有任何数据源的情况下，向上触发获取数据，就像水流往下流动。<p>
 * 在当前层仍有数据的情况下，则直接向下层发送剩余事件。<p>
 * 当最底层reduce操作拿不到任何数据时，即所有层都没有数据源了，跳出循环返回reduce结果。<p>
 *
 * <pre>
 *  A,B,C ->
 *          ---+
 *        OP 1 |           <--  (map)
 *             +---+
 *            OP 2 |       <--  (flatMap)
 *                 +---+
 *                OP 3 |   <--  (map)
 *                     +---
 *                         -> Reduce
 * </pre>
 */
public class MyStream<T> implements Up {

    final List<T> sourceList = new ArrayList<>();

    Function op = null;
    MyStream next = null;

    Up up = null;

    @Override
    public void trigger() {
        if (up != null && sourceList.size() == 0) {
            // 不是顶层，且当前没有数据了，触发上游下发数据
            // 若是顶层，或当前层仍有数据，那么不触发上游，仍从本层拿数据给下游
            up.trigger();
        }

        // 捕获上游传递的数据
        T item = capture();

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
            Object opResult = op.apply(source);
            if (opResult instanceof MyStream) {
                // 执行结果为 MyStream 类型，即 flatmap 操作，将转换后数据源打包给下游
                next.sourceList.addAll(((MyStream) opResult).sourceList);
            } else {
                next.sourceList.add(opResult);
            }
        }
    }

    public <R> MyStream<R> map(Function<T, R> mapper) {
        this.op = mapper;
        MyStream<R> nextStream = new MyStream<>();
        nextStream.up = this;
        this.next = nextStream;
        return nextStream;
    }

    public <R> MyStream<R> flatMap(Function<T, MyStream<R>> flatMapper) {
        this.op = flatMapper;
        MyStream<R> nextStream = new MyStream<>();
        nextStream.up = this;
        this.next = nextStream;
        return nextStream;
    }

    public T reduce(BinaryOperator<T> binaryOperator) {
        List<T> reduceList = new ArrayList<>();
        while (true) {
            if (up != null) {
                up.trigger();
            }

            if (sourceList.size() > 0) {
                reduceList.add(sourceList.get(0));
                sourceList.clear();
            } else {
                // 通过 Trigger 拿不到更多数据，说明上游已经没有数据，退出循环
                break;
            }

            if (reduceList.size() == 2) {
                // reduce 序列达到两个元素，进行reduce操作
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

    public static <S> MyStream<S> of(S[] input) {
        MyStream<S> stream = new MyStream<>();
        Collections.addAll(stream.sourceList, input);
        return stream;
    }


}
