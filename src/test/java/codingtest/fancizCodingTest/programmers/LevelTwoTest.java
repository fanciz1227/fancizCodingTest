package codingtest.fancizCodingTest.programmers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class LevelTwoTest {

    /**
     * 두 수의 최소공배수(Least Common Multiple)란 입력된 두 수의 배수 중 공통이 되는 가장 작은 숫자를 의미합니다.
     * 예를 들어 2와 7의 최소공배수는 14가 됩니다. 정의를 확장해서, n개의 수의 최소공배수는 n 개의 수들의 배수 중 공통이 되는 가장 작은 숫자가 됩니다.
     * n개의 숫자를 담은 배열 arr이 입력되었을 때 이 수들의 최소공배수를 반환하는 함수, solution을 완성해 주세요.
     */
    @Test
    public void N개배열의최소공배수() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 168;
        int[] arr = {2,6,8,14};
        int lcmNum = arr[0];

        //주어진 배열의 요소만큼 반복해준다.
        //2,6 -> 6,8 -> 24,14 의 반복을 거친다.
        //첫번째만 배열의 0번째 요소를 이용해서 풀고 그 이후로는 구해진 최대공배수와 arr와 값을 이용해 다시 최대공배수를 구해주고 그걸 배열갯수-1만큼 반복하면 배열의 요소만큼 최소공배수가 구해진다.
        for (int i=1; i<arr.length; i++) {
            lcmNum = lcm(lcmNum, arr[i]);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + lcmNum);
        assertThat(answer, is(lcmNum));
    }

    //a,b를 곱한 수를 구해진 최대 공약수로 나눠주면 최소공배수가 구해진다.
    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    //재귀함수 gcd를 통해 a 나누기 b의 값이 0이 될때까지 계속 나누어 최대 공약수를 구해준다.
    private int gcd(int a, int b) {
        if (a%b == 0) return b;
        return gcd(b, a % b);
    }

    /**
     * 문자열 s에는 공백으로 구분된 숫자들이 저장되어 있습니다. str에 나타나는 숫자 중 최소값과 최대값을 찾아 이를 "(최소값) (최대값)"형태의 문자열을 반환하는 함수, solution을 완성하세요.
     * 예를들어 s가 "1 2 3 4"라면 "1 4"를 리턴하고, "-1 -2 -3 -4"라면 "-4 -1"을 리턴하면 됩니다.
     */
    @Test
    public void 최대값과최소값() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "1 4";
        String s = "1 2 3 4";

        String[] sArr = s.split(" ");
        int minNo = Integer.parseInt(sArr[0]);
        int maxNo = Integer.parseInt(sArr[0]);

        //linear search - 선형검색을 이용해서 풀었는데.. programmers에서는 평균 7.02ms가 걸린다.
        //7ms가 빠르다고 느껴지진 않는다.. 배열의 수가 늘어나면 늘어날 수록 시간이 더 걸리는듯한데 다른 방법이 뭐가 있을까?
        for (int i=1; i<sArr.length; i++) {
            int n = Integer.parseInt(sArr[i]);

            if(maxNo < n) maxNo = n;
            if(minNo > n) minNo = n;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + minNo + " " + maxNo);
        assertThat(answer, is(minNo + " " + maxNo));
    }

    /**
     * 2차원 행렬 arr1과 arr2를 입력받아, arr1에 arr2를 곱한 결과를 반환하는 함수, solution을 완성해주세요.
     */
    @Test
    public void 행렬의곱셈() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[][] answer = {{22,22,11}, {36,28,18}, {29,20,14}};
        int[][] arr1 = {{2,3,2}, {4,2,4}, {3,1,4}};
        int[][] arr2 = {{5,4,3}, {2,4,1}, {3,1,1}};
        int[][] result = new int[arr1.length][arr2[0].length];

        //오랜만에 행렬의 곱셉 식을 찾아봤다.
        //(0,0)*(0,0) + (0,1)*(1,0) + (0,2)*(2,0) = (0,0) 과 같은 수식으로 차례대로 넣어야한다.
        //곱하는 배열의 수식은 arr1, arr2에 따라서 result의 배열속성이 정해지며
        //result를 생성할 때 크기 옵션을 arr2[0].length로 줘야 값이 잘못 생성되지 않는다.
        for (int i=0; i<arr1.length; i++) {
            for (int j=0; j<arr2[0].length; j++) {
                for (int x=0; x<arr1[0].length; x++) {
                    result[i][j] += arr1[i][x] * arr2[x][j];
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        assertThat(answer, is(result));
    }

    /**
     * 한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.
     * 각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.
     */
    public static List<Integer> permutationList = new ArrayList<>();

    @Test
    public void 소수찾기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        String numbers = "011";
        String[] nArr = numbers.split("");
        String[] output = new String[nArr.length];
        boolean[] visited = new boolean[nArr.length];
        int n = nArr.length;
        int r = nArr.length;

        //dfs 재귀 호출 시작
        dfs(nArr, output, visited, 0, n, r);

        //소수만 찾아서 카운트 증가 후 리턴 받는다.
        int decimalCnt = findPrime(permutationList.stream().distinct().collect(Collectors.toList()));

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(decimalCnt));
    }

    //dfs를 통해 모든 순열의 데이터를 얻는다.
    private void dfs(String[] arr, String[] output, boolean[] visited, int depth, int n, int r) {
        if (depth == r) {
            setPermutationList(output);
            return;
        }

        for (int i=0; i<n; i++) {
            if (!visited[i]) { //방문한 노드가 아니면
                visited[i] = true; //방문처리를 먼저 한다.
                output[depth] = arr[i];
                dfs(arr, output, visited, depth+1, n, r);
                visited[i] = false;
            }
        }
    }

    //순열로 얻은 모든 데이터들을 숫자화 시킨다.
    private void setPermutationList(String[] output) {
        StringBuilder sb = new StringBuilder();

        //재귀 처리를 하면서 나오게 되는 경우의 수 배열을 for문으로 하나의 숫자처럼 처리한다.
        for(int i=0; i<output.length; i++) {

            sb.append(output[i]);
            //순열을 돌리면서 나온 모든 경우의수를 붙여서 숫자로 인식하게 한다.
            //ex) [1,0,1] => 101
            permutationList.add(Integer.parseInt(sb.toString()));
        }
    }

    //소수 찾기
    private int findPrime(List<Integer> list) {
        int cnt = 0;

        //중복체크하여 정렬한 데이터의 size만큼 돈다.
        for (int i=0; i<list.size(); i++) {
            boolean isPrime = true;

            if(list.get(i).intValue() > 1) { // 0,1은 소수가 아니므로 제외시킨다.
                for (int j=2; j*j<=list.get(i).intValue(); j++) { //루트를 이용하여 타임아웃을 방지한다.
                    if(list.get(i).intValue() % j == 0) {
                        isPrime = false;
                        break;
                    }
                }

                if(isPrime) cnt++;
            }
        }

        return cnt;
    }

    /**
     * Finn은 요즘 수학공부에 빠져 있습니다. 수학 공부를 하던 Finn은 자연수 n을 연속한 자연수들로 표현 하는 방법이 여러개라는 사실을 알게 되었습니다. 예를들어 15는 다음과 같이 4가지로 표현 할 수 있습니다.
     * 1 + 2 + 3 + 4 + 5 = 15
     * 4 + 5 + 6 = 15
     * 7 + 8 = 15
     * 15 = 15
     * 자연수 n이 매개변수로 주어질 때, 연속된 자연수들로 n을 표현하는 방법의 수를 return하는 solution를 완성해주세요.
     */
    @Test
    public void 숫자의_표현() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 4;
        int result = 0;
        int n = 15;

        for (int i=1; i<=n; i++) {
            int inum = i;
            int add = 0;

            while (true) {
                add += inum;

                if (add == n) {
                    result++;
                    break;
                } else if (add > n) {
                    break;
                }

                inum++;
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 자연수 n이 주어졌을 때, n의 다음 큰 숫자는 다음과 같이 정의 합니다.
     *
     * 조건 1. n의 다음 큰 숫자는 n보다 큰 자연수 입니다.
     * 조건 2. n의 다음 큰 숫자와 n은 2진수로 변환했을 때 1의 갯수가 같습니다.
     * 조건 3. n의 다음 큰 숫자는 조건 1, 2를 만족하는 수 중 가장 작은 수 입니다.
     * 예를 들어서 78(1001110)의 다음 큰 숫자는 83(1010011)입니다.
     *
     * 자연수 n이 매개변수로 주어질 때, n의 다음 큰 숫자를 return 하는 solution 함수를 완성해주세요.
     */
    @Test
    public void 다음_큰_숫자() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 83;
        int n = 78;
        int result = 0;
        int nBinaryCnt = this.decimalToBinary(n);

        //2진수로 변환 후에 1을 카운트 해주는 로직을 썼는데 Integer class에 있는 Integer.bitCount(n) 를 이용하면 더 간단하게 구현이 가능하다.
        while (true) {
            n++;

            if (nBinaryCnt == this.decimalToBinary(n)) {
                result = n;
                break;
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    private int decimalToBinary(int n) {
        int cnt = 0;

        while (n > 0) {
            if ((n % 2) == 1) cnt++;
            n /= 2;
        }

        return cnt;
    }

    /**
     * 괄호가 바르게 짝지어졌다는 것은 '(' 문자로 열렸으면 반드시 짝지어서 ')' 문자로 닫혀야 한다는 뜻입니다. 예를 들어
     * "()()" 또는 "(())()" 는 올바른 괄호입니다.
     * ")()(" 또는 "(()(" 는 올바르지 않은 괄호입니다.
     * '(' 또는 ')' 로만 이루어진 문자열 s가 주어졌을 때, 문자열 s가 올바른 괄호이면 true를 return 하고, 올바르지 않은 괄호이면 false를 return 하는 solution 함수를 완성해 주세요.
     */
    @Test
    public void 올바른_괄호() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final boolean answer = false;
        boolean result = true;
        String s = "())()";

        /*
        int cnt = 0;

        //처음에 split을 이용해서 배열로 문제를 풀었는데 시간초과가 나길래 이상하다 싶었는데 charAt으로 하니까 된다..
        //참나 뭔 함정같지도 않은 함정을 넣어놨다
        for (int i=0; i<sArr.length; i++) {
            if('(' == s.charAt(i)) cnt++; //(이면 추가하고
            if(')' == s.charAt(i)) cnt--; //)이면 뺀다

            if (cnt < 0) break; //근데 cnt가 0인 상태에서 )이여서 빼게되면 그건 잘못된 순서로 인식하기때문에 break해서 false로 처리한다. 이때 answer의 초기값은 false로 해야지만 정상 처리 된다.
        }

        if (cnt == 0) answer = true; //정상적인 순서대로 없어지게 되면 cnt가 0이기 떄문에 true로 처리한다.

         */

        //스택을 이용한 풀이다.
        //(일때만 push를 해주고 )일때는 pop을 해서 추출해내는 방식이다.
        Stack<Character> stack = new Stack<>();

        for (int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);

            if ('(' == ch) {
                stack.push('(');
            } else {
                if (stack.isEmpty()) { //중간에 ( 가 푸시되지 않은 상태에서 )을 pop하려고 했을때 푸시된 데이터가 없으므로 잘못된 순서로 인식하여 false로 리턴한다.
                    result = false;
                    break;
                }
                stack.pop();
            }
        }

        if (!stack.isEmpty()) result = false; //스택내에 남은값이 있으면 완벽하게 처리된 문장이 아니므로 false를 리턴한다.

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 길이가 같은 배열 A, B 두개가 있습니다. 각 배열은 자연수로 이루어져 있습니다.
     * 배열 A, B에서 각각 한 개의 숫자를 뽑아 두 수를 곱합니다. 이러한 과정을 배열의 길이만큼 반복하며, 두 수를 곱한 값을 누적하여 더합니다. 이때 최종적으로 누적된 값이 최소가 되도록 만드는 것이 목표입니다. (단, 각 배열에서 k번째 숫자를 뽑았다면 다음에 k번째 숫자는 다시 뽑을 수 없습니다.)
     * 예를 들어 A = [1, 4, 2] , B = [5, 4, 4] 라면
     * A에서 첫번째 숫자인 1, B에서 첫번째 숫자인 5를 뽑아 곱하여 더합니다. (누적된 값 : 0 + 5(1x5) = 5)
     * A에서 두번째 숫자인 4, B에서 세번째 숫자인 4를 뽑아 곱하여 더합니다. (누적된 값 : 5 + 16(4x4) = 21)
     * A에서 세번째 숫자인 2, B에서 두번째 숫자인 4를 뽑아 곱하여 더합니다. (누적된 값 : 21 + 8(2x4) = 29)
     * 즉, 이 경우가 최소가 되므로 29를 return 합니다.
     * 배열 A, B가 주어질 때 최종적으로 누적된 최솟값을 return 하는 solution 함수를 완성해 주세요.
     */
    @Test
    public void 최솟값_만들기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 29;
        int result = 0;
        int[] A = {1, 4, 2};
        int[] B = {5, 4, 4};

        Arrays.sort(A);
        Arrays.sort(B);

        for (int i=0; i<A.length; i++) {
            result += A[i] * B[(A.length - 1) - i]; //첫번째 배열을 오름차순, 두번째 배열은 내림차순으로 정렬하여 서로 각 i값에 맞는 값을 곱해주면 된다.
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 선행 스킬이란 어떤 스킬을 배우기 전에 먼저 배워야 하는 스킬을 뜻합니다.
     * 예를 들어 선행 스킬 순서가 스파크 → 라이트닝 볼트 → 썬더일때, 썬더를 배우려면 먼저 라이트닝 볼트를 배워야 하고, 라이트닝 볼트를 배우려면 먼저 스파크를 배워야 합니다.
     * 위 순서에 없는 다른 스킬(힐링 등)은 순서에 상관없이 배울 수 있습니다. 따라서 스파크 → 힐링 → 라이트닝 볼트 → 썬더와 같은 스킬트리는 가능하지만, 썬더 → 스파크나 라이트닝 볼트 → 스파크 → 힐링 → 썬더와 같은 스킬트리는 불가능합니다.
     * 선행 스킬 순서 skill과 유저들이 만든 스킬트리1를 담은 배열 skill_trees가 매개변수로 주어질 때, 가능한 스킬트리 개수를 return 하는 solution 함수를 작성해주세요.
     * 제한 조건
     * 스킬은 알파벳 대문자로 표기하며, 모든 문자열은 알파벳 대문자로만 이루어져 있습니다.
     * 스킬 순서와 스킬트리는 문자열로 표기합니다.
     * 예를 들어, C → B → D 라면 "CBD"로 표기합니다
     * 선행 스킬 순서 skill의 길이는 1 이상 26 이하이며, 스킬은 중복해 주어지지 않습니다.
     * skill_trees는 길이 1 이상 20 이하인 배열입니다.
     * skill_trees의 원소는 스킬을 나타내는 문자열입니다.
     * skill_trees의 원소는 길이가 2 이상 26 이하인 문자열이며, 스킬이 중복해 주어지지 않습니다.
     */
    @Test
    public void 스킬트리() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        int result = 0;
        String skill = "CBD";
        String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA", "CED"};

        for (String str : skill_trees) {
            if (!skill.isBlank()) { //skill이 없는 경우도 있기 때문에 체크한다.
                List<String> skList = new ArrayList<>(Arrays.asList(skill.split("")));
                List<String> stList = new ArrayList<>(Arrays.asList(str.split("")));
                HashMap<String, Integer> skillMap = new HashMap<>();

                for (String tree : stList) {
                    if (skList.indexOf(tree) > -1) skillMap.put(tree, stList.indexOf(tree)); //skill tree list만큼 돌면서 skill내에 존재한다면 skillMap에 찾은 스킬과 현재 skill_trees에 선언된 index 값을 넣어준다.
                }

                List<Map.Entry<String, Integer>> entryList = new ArrayList<>(skillMap.entrySet());
                entryList.sort(Map.Entry.comparingByValue()); //위에서 수집된 skillMap을 value로 오름차순 정렬을 해준다.
                StringBuilder sb = new StringBuilder();

                for (Map.Entry<String, Integer> entry : entryList) {
                    sb.append(entry.getKey()); //value로 정렬된 값을 하나의 문자로 만들어주면 BCD, CBD, CB, BD, CD 와 같은 순서 문자를 찾는다.
                }

                if (skill.indexOf(sb.toString()) == 0) result++; //skill에서 하나의 문자로 만든 값을 indexOf로 찾았을때 정상적인 순서로 있다면 C부터 값이 차례대로 선언되어있기 때문에 index값이 0이 나와야한다. 따라서 0인 값만 카운트 해주면 처리된다.

            } else { //skill이 없으면 어떻게 찍던 skill tree와는 상관이 없기 때문에 모든 요소를 다 카운트 해주면 된다.
                result++;
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    @Test
    public void 스킬트리_개선() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        int result = 0;
        String skill = "CBD";
        String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA", "CED"};

        /*
        다른 사람이 푼 문제인데 내가 찾으려했던 indexOf 값을 skill에 있는 선언되어 있는 문자를 정규표현식을 이용해서 풀었다.
        나는 대략 30줄짜리 코드를 짜고 불필요한 list선언들이 3번이나 있었던거에 반면 아래 코드는 정규표현식을 이용해서 간단하게 처리했다.

        ArrayList<String> skillTrees = new ArrayList<String>(Arrays.asList(skill_trees));
        Iterator<String> it = skillTrees.iterator();

        while (it.hasNext()) {
            //정규표현식 [^CBD]를 이용해서 해당 문자가 있는지 파악하고 나머지 값을 ""로 치환함과 동시에 indexOf를 통해 순서대로 선언되어있는 값인지 판단하여 0이 아니면(순서가 틀렸다면) iterator에서 지워준다.
            if (skill.indexOf(it.next().replaceAll("[^" + skill + "]", "")) != 0) {
                it.remove();
            }
        }

        answer = skillTrees.size(); //while에서 순서가 틀려서 remove된 요소를 제외하고 남아있는 size를 찾으면 정답이 된다.
         */

        //위에 다른 사람이 푼 방식을 iterator와 arraylist를 제거하고 개선해보았다. 시간복잡도는 내가 풀었던 방식보다 좋은듯한데 실제로 시행해보니 수행시간이 차이가 꽤 있다..
        //내가 푼 방식은 2~3ms인 반면 정규표현식으로 for문을 하나만 돌렸음에도 불구하고 6~8ms가 걸렸다. 이러한 이유가 왜인지 한번 알아봐야할듯하다..
        for (String tree : skill_trees) {
            if (skill.indexOf(tree.replaceAll("[^" + skill + "]", "")) == 0) result++;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * n초 간의 주가를 초 단위로 기록한 배열 prices가 매개변수로 주어질 때, 각 초의 주가를 기준으로 해당 초 부터 n초 사이에 가격이 떨어지지 않은 시간은 몇 초인지 배열에 담아 return 하도록 solution 함수를 완성하세요.\
     * 입출력 예 설명
     * 1초의 주가는 1이며 1초부터 5초까지 총 4초간 주가를 유지했습니다.
     * 2초의 주가는 2이며 2초부터 5초까지 총 3초간 주가를 유지했습니다.
     * 3초의 주가는 3이며 4초의 주가는 2로 주가가 떨어졌지만 3초에서 4초가 되기 직전까지의 1초간 주가가 유지 된것으로 봅니다. 따라서 5초까지 총 1초간 주가를 유지했습니다.
     * 4초의 주가는 2이며 4초부터 5초까지 총 1초간 주가를 유지했습니다.
     * 5초의 주가는 3이며 5초 이후로는 데이터가 없으므로 총 0초간 주가를 유지했습니다.
     */
    @Test
    public void 주식가격() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {4,3,1,1,0};
        int[] prices = {1,2,3,2,3};
        int[] result = new int[prices.length];

        for (int i=0; i<prices.length; i++) {
            int cnt = 0;

            for (int j=i+1; j<prices.length; j++) { //i번의 다음 숫자들과 비교해야하기 때문에 시작을 i+1로 해준다.
                cnt++; //다음 숫자에 수가 바뀌어도 1초는 유지된것으로 판단하기 때문에 기본적으로 cnt를 늘려준다.
                if (prices[i] > prices[j]) break; //i번째 수가 j번째 수보다 작으면 주가 유지에 실패했기 때문에 for문을 벗어난다.
            }

            result[i] = cnt;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다. 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.
     * 섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
     * Leo는 모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
     * Leo가 가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때, 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 더_맵게() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        int[] scoville = {1, 2, 3, 9, 10, 12};
        int K = 7;
        int result = 0;
        PriorityQueue<Integer> que = new PriorityQueue<>(); //우선순위큐를 활용해서 풀어야 효율성 테스트 통과가 가능함.

        for (int scv : scoville) { //스코빌 배열 데이터를 우선순위큐에 먼저 대입한다.
            que.add(scv);
        }

        while(que.size() > 1 && que.peek() < K) {
            if (que.size() <= 1 && que.peek() > K) break;

            int firstHot = que.poll();
            int secondHot = que.poll();

            que.add(firstHot + (secondHot * 2));
            result++;
        }

        if (que.size() <= 1 && que.peek() < K) result = -1;

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 피보나치 수는 F(0) = 0, F(1) = 1일 때, 1 이상의 n에 대하여 F(n) = F(n-1) + F(n-2) 가 적용되는 수 입니다.
     * 예를들어
     * F(2) = F(0) + F(1) = 0 + 1 = 1
     * F(3) = F(1) + F(2) = 1 + 1 = 2
     * F(4) = F(2) + F(3) = 1 + 2 = 3
     * F(5) = F(3) + F(4) = 2 + 3 = 5
     * 와 같이 이어집니다.
     * 2 이상의 n이 입력되었을 때, n번째 피보나치 수를 1234567으로 나눈 나머지를 리턴하는 함수, solution을 완성해 주세요.
     */
    @Test
    public void 피보나치_수() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        int n = 3;
        int[] fiboArr = new int[n+1]; //기존 배열은 0부터 시작하기 때문에 +1을 해줘서 index가 n이 될 수 있게 선언한다.
        fiboArr[0] = 0; //F(0)은 0이다.
        fiboArr[1] = 1; //F(1)은 1이다.

        for (int i=2; i<=n; i++) { //2이상의 값부터 처리해준다. n이 2미만 일 경우 그냥 fiboArr에 있는 fiboArr[n]값으로 대체되어 별도 처리 없이 바로 리턴한다.
            fiboArr[i] = (fiboArr[i-1] + fiboArr[i-2]) % 1234567; //i가 2일경우 F(2) = F(2-1) + F(2-2) => 즉 F(2) = F(0) + F(1) = 1이다. 여기서 더한값을 1234567로 나눠서 나머지를 해당 배열 값에 넣어준다.
            //1234567로 나눈 이유는 int라는 자료형은 -2,147,483,648 ~ 2,147,483,647까지의 값만을 표현할 수 있는데 피보나치 수열은 값을 계속 자가증식하므로 n=44만 되어도 int형의 값을 넘어서버린다.
            // 따라서 매번 피보나치수를 1234567로 나눠서 int형 자료형에 맞게 쓸 수 있도록 한것이다.
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(fiboArr[n]));
    }
}