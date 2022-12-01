package homework5;

	public interface Sets {
    static Set<Set<Object>> powerSet(Set<Object> set) {
        if(set.size() >= 15) {
            throw new IllegalArgumentException();
        }

        Set<Set<Object>> resultSet = new HashSet<>();
        Deque<Object> queue = new ArrayDeque<>();
        Object[] arraySet = set.toArray();
        findPowerSet(arraySet, queue, set.size(), resultSet);

        return resultSet;
    }

    static void findPowerSet(Object[] input, Deque<Object> dq, int n, Set<Set<Object>> resultSet) {
        if (n == 0) {
            resultSet.add(new HashSet<>(dq));
            return;
        }

        dq.addLast(input[n - 1]);
        findPowerSet(input, dq, n - 1, resultSet);

        dq.removeLast();
        findPowerSet(input, dq, n - 1, resultSet);
    }

    static List<Integer> multiples(Set<Integer> numSet, int multiple) {
        List<Integer> filteredSortedList = new ArrayList<>();
        numSet.forEach( number -> {
            if(number % multiple == 0) {
                filteredSortedList.add(number);
            }
        });
        Collections.sort(filteredSortedList);
        return filteredSortedList;
    }
}
