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

    /**
     * 124 나라가 있습니다. 124 나라에서는 10진법이 아닌 다음과 같은 자신들만의 규칙으로 수를 표현합니다.
     * 124 나라에는 자연수만 존재합니다.
     * 124 나라에는 모든 수를 표현할 때 1, 2, 4만 사용합니다.
     * 예를 들어서 124 나라에서 사용하는 숫자는 다음과 같이 변환됩니다.
     * 10진법 124나라 10진법 124나라
     * 1	1	6	14
     * 2	2	7	21
     * 3	4	8	22
     * 4	11	9	24
     * 5	12	10	41
     * 자연수 n이 매개변수로 주어질 때, n을 124 나라에서 사용하는 숫자로 바꾼 값을 return 하도록 solution 함수를 완성해 주세요.
     */
    @Test
    public void 일이사_나라의_숫자() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "12";
        int n = 5;
        StringBuilder sb = new StringBuilder();

        /*while (n > 0) {
            int mok = n % 3; //n을 3으로 나눠준다.

            if (mok == 0) { //n을 3으로 나눈 몫이 0 즉 3일때는 124나라에서는 4가 되어야하기 때문에 4를 대입해준다.
                sb.append(4);
                n = (n / 3) - 1; //n이 3의 배수이기 때문에 n/3값에 -1을 해줘서 n을 계산해준다.
            } else {
                sb.append(mok);
                n /= 3; //3진수기때문에 0,1,2로 값이 생성되기 때문에 나눠 떨어지는 0을 제외한 나머지는 그대로 대입하고 다시 3으로 나눈 나머지를 n에 대입해준다.
            }
        }*/

        //배열을 이용한 풀이도 있길래 가져와봤다.
        //3진수로 바꿀때 012가 되기때문에 배열에 대입하여 문제를 풀 수 있다.
        String[] num = {"4","1","2"};

        while (n > 0) {
            sb.append(num[n % 3]);
            n = (n - 1) / 3; //이 부분이 관건인데.. n-1을 통해 한자리씩 땡기면서 모듈러 연산을 했다고 하는데 솔직히 잘 모르겠다.. 수학을 잘하면 소스가 이렇게나 간단해질 수 있다니 ㅠ
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(sb.reverse().toString()));
    }

    /**
     * JadenCase란 모든 단어의 첫 문자가 대문자이고, 그 외의 알파벳은 소문자인 문자열입니다. 문자열 s가 주어졌을 때, s를 JadenCase로 바꾼 문자열을 리턴하는 함수, solution을 완성해주세요.
     *
     * 제한 조건
     * s는 길이 1 이상인 문자열입니다.
     * s는 알파벳과 공백문자(" ")로 이루어져 있습니다.
     * 첫 문자가 영문이 아닐때에는 이어지는 영문은 소문자로 씁니다. ( 첫번째 입출력 예 참고 )
     */
    @Test
    public void JadenCase_문자열_만들기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "  People Unfollowed Me";
        String s = "  people unFollowed me";
        StringBuilder sb = new StringBuilder();

        /*String[] sArr = s.split(""); //한글자씩 잘라서 배열로 만든다.

        int index = 0;

        for (int i=0; i<sArr.length; i++) {
            if (" ".equals(sArr[i])) { //공백일 경우 공백을 넣어주고 continue 한다.
                sb.append(" ");
                index = 0; //공백이 나왔다면 문자가 다시 시작되는 경우이므로 index를 0으로 해서 첫글자인지 판단한다.
                continue;
            }

            if (index == 0) { //index가 0이면 첫글자로 간주한다.
                index = 1; //첫글자 처리는 끝났기 때문에 index에 1을 넣어 다음 배열문자에서는 첫글자가 아닌것으로 세팅해준다.
                char firstCh = sArr[i].charAt(0); //공백이 아닐경우 숫자인지 판별한다.

                if (48 <= firstCh && firstCh <= 57) { //숫자면 그냥 append하고 continue 한다.
                    sb.append(sArr[i]);
                    continue;
                } else { //숫자가 아닌 문자면 대문자로 치환해서 append하고 continue 한다.
                    sb.append(sArr[i].toUpperCase());
                    continue;
                }
            }

            if (index > 0) { //index가 0보다 크므로 첫글자가 아니기 때문에 소문자로 치환한다.
                sb.append(sArr[i].toLowerCase());
            }
        }*/

        //위에 길고 긴 저 소스들도 문제를 통과하긴 했지만 더 짧고 간결한 소스가 있었다..
        String[] sArr = s.toLowerCase().split(""); //모두 소문자로 바꾼뒤 한글자씩 자른다.
        boolean first = true; //첫글자 유무를 판단해서 boolean으로 처리해준다.

        for (String str : sArr) {
            sb.append(first ? str.toUpperCase() : str); //모두 소문자인 상태이기 때문에 첫글자라면 대문자로 바꾼다. 숫자는 안먹기때문에 자연스럽게 패스된다.
            first = " ".equals(str) ? true : false; //현재 문자가 공백일 경우 문자가 끝났다고 봐야하기 때문에 first를 true로 만들고 그 외에 문자들은 false로 만들어 대문자로 치환되지 않게 한다.
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(sb.toString()));
    }

    /**
     * 게임 캐릭터를 4가지 명령어를 통해 움직이려 합니다. 명령어는 다음과 같습니다.
     * U: 위쪽으로 한 칸 가기
     * D: 아래쪽으로 한 칸 가기
     * R: 오른쪽으로 한 칸 가기
     * L: 왼쪽으로 한 칸 가기
     * 캐릭터는 좌표평면의 (0, 0) 위치에서 시작합니다. 좌표평면의 경계는 왼쪽 위(-5, 5), 왼쪽 아래(-5, -5), 오른쪽 위(5, 5), 오른쪽 아래(5, -5)로 이루어져 있습니다.
     */
    @Test
    public void 방문_길이() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 1;
        String dirs = "LRLRL";
        int n = 5;
        int x = 0;
        int y = 0;
        int result = 0;

        String[] locate = dirs.split("");
        List<String> visitLocateArr = new ArrayList<>();

        //순서대로 조합하면 L,R,U,D
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (String str : locate) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbReverse = new StringBuilder();
            int nx = 0;
            int ny = 0;

            switch (str) {
                case "L" :
                    nx = x + dx[0];
                    ny = y + dy[0];
                    break;
                case "R" :
                    nx = x + dx[1];
                    ny = y + dy[1];
                    break;
                case "U" :
                    nx = x + dx[2];
                    ny = y + dy[2];
                    break;
                case "D" :
                    nx = x + dx[3];
                    ny = y + dy[3];
                    break;
                default: break;
            }

            //정방향으로 갔을때의 루트를 입력한다.
            sb.append(x);
            sb.append(y);
            sb.append(nx);
            sb.append(ny);

            //반대방향으로 이동하는 루트도 구해준다.
            sbReverse.append(nx);
            sbReverse.append(ny);
            sbReverse.append(x);
            sbReverse.append(y);

            String visited = sb.toString(); //왼->오 로 방문했다고 쳤을때
            String visitedReverse = sbReverse.toString(); //오->왼 으로 이동한 루트도 같이 구해준다.

            if (Math.abs(nx) > n || Math.abs(ny) > n) continue; //방문한 지점이 맵의 최대크기인 5, -5 총 10 사이즈를 벗어나게 된다면 체크하지 않고 다음 루트를 체크한다.
            x = nx; //이동한 지점을 현재 지점으로 대입한다.
            y = ny; //이동한 지점을 현재 지점으로 대입한다.

            if (visitLocateArr.indexOf(visited) > -1) continue; //방향성이 없는 방문루트를 구해야하기 때문에 위에서 visited, visitedReverse를 같이 구해서 list에 넣어주고 그 list 안에서 이동한 흔적이 있는지 찾는다.
            result++; //모든 조건을 통과하면 한번도 방문한적 없는 루트로 간것이기떄문에 카운트해준다.
            visitLocateArr.add(visited); //방문 리스트에 정방향, 역방향 모두 데이터를 넣어줘서 방향이 안맞는 동일한 루트도 검색할 수 있게 해준다.
            visitLocateArr.add(visitedReverse);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 일반적인 프린터는 인쇄 요청이 들어온 순서대로 인쇄합니다. 그렇기 때문에 중요한 문서가 나중에 인쇄될 수 있습니다. 이런 문제를 보완하기 위해 중요도가 높은 문서를 먼저 인쇄하는 프린터를 개발했습니다. 이 새롭게 개발한 프린터는 아래와 같은 방식으로 인쇄 작업을 수행합니다.
     *
     * 1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
     * 2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
     * 3. 그렇지 않으면 J를 인쇄합니다.
     *
     * 예를 들어, 4개의 문서(A, B, C, D)가 순서대로 인쇄 대기목록에 있고 중요도가 2 1 3 2 라면 C D A B 순으로 인쇄하게 됩니다.
     * 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 알고 싶습니다. 위의 예에서 C는 1번째로, A는 3번째로 인쇄됩니다.
     * 현재 대기목록에 있는 문서의 중요도가 순서대로 담긴 배열 priorities와 내가 인쇄를 요청한 문서가 현재 대기목록의 어떤 위치에 있는지를 알려주는 location이 매개변수로 주어질 때, 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 프린터() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 5;
        int location = 0;
        int[] priorities = {1, 1, 9, 1, 1, 1};

        Queue<Print> queue = new LinkedList<>();

        //인쇄 작업 대기열 priorities 만큼 for문으로 queue 대기열을 만들어 준다.
        for (int i=0; i<priorities.length; i++) {
            Print print = new Print(priorities[i], i == location ? true : false); //location과 i가 일치하면 true로 대입해준다.

            queue.offer(print);
        }

        int printCount = 0;

        while (!queue.isEmpty()) { //queue 내에 모든 요소가 사라질때까지 무한 반복한다.
            Print pollPrint = queue.poll(); //큐는 선입 선출이기 떄문에 가장 앞 순서 부터 꺼내온다.

            if (queue.stream().anyMatch(a -> a.getPriorities() > pollPrint.getPriorities())) { //꺼내온 첫 데이터에서 대기열 내 다른 큰 숫자가 존재한다면
                queue.offer(pollPrint); //가장 앞 순서에서 꺼내온 pollPrint를 다시 가장 최후방으로 입력시킨다.
            } else { //꺼내온 첫 데이터에서 대기열 내 다른 큰 숫자가 존재하지 않는다면 출력했다고 판단하여 printCount를 증가시킨다.
                printCount++;

                if (pollPrint.isLocation()) { //출력한 location이 내 대기열이라면 더이상 while을 반복할 필요가 없기 때문에 break하여 while문을 종료시킨다.
                    break;
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(printCount));
    }

    private class Print {
        int priorities;
        boolean location;

        public int getPriorities() {
            return priorities;
        }

        public boolean isLocation() {
            return location;
        }

        Print(int pri, boolean locate) {
            this.priorities = pri;
            this.location = locate;
        }
    }

    /**
     * 프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.
     * 또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.
     * 먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 return 하도록 solution 함수를 완성하세요.
     */
    @Test
    public void 기능개발() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int[] answer = {1, 3, 2};
        int[] progresses = {95, 90, 99, 99, 80, 99};
        int[] speeds = {1, 1, 1, 1, 1, 1};

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> completeList = new ArrayList<>();

        //기능개발에 완료되는 일정을 for문으로 queue에다 세팅해준다.
        for (int i=0; i<progresses.length; i++) {
            queue.offer((int) Math.ceil((100 - progresses[i]) / (double) speeds[i]));
        }

        int firstCmp = queue.poll(); //while문을 돌리기위해 초기 데이터를 가장 첫데이터로 세팅한다.
        int cnt = 1; //첫데이터는 있기때문에 cnt를 1로 시작해준다.
        int day = firstCmp; //첫 배포가능 일자를 세팅한다.
        boolean end = false; //개발완료여부를 초기에는 false로 지정한다.

        while (true) {
            if (queue.isEmpty()) { //while문으로 무한반복을 하다 더이상 꺼낼 큐가 없다면 종료 시킴과 동시에 현재까지 쌓은 cnt를 list에 추가하고 종료시킨다.
                completeList.add(cnt);
                break;
            }

            int completeDate = queue.peek(); //우선 큐에 존재하는 값을 바로 꺼내지 않고 참조하여 사용한다.

            if (day >= completeDate) { //참조해서 꺼낸 날짜 값이 day보다 작거나 같으면 큐에서 꺼냄과 동시에 cnt를 진행한다.
                queue.poll(); //같은 배포일에 배포할 수 있을때만 배포 카운트에 포함해야하기 때문에 이때만 큐에서 꺼내준다.
                cnt++;
            } else { //참조해서 꺼낸 값이 day보다 크면 더 이상 배포할수 없다고 판단하여 종료처리를 한다.
                day = completeDate; //비교값인 day를 큐에서 꺼낸 더 큰 날짜로 변경한다.
                end = true; //여태까지 쌓은 cnt를 list에 넣어야하기 떄문에 종료값으로 바꾼다.
            }

            if (end) { //종료값으로 인식될 경우 여태까지 쌓은 cnt를 list에 대입한다.
                completeList.add(cnt);
                end = false; //다시 반복문을 돌기 위해 종료값은 false로 cnt는 0으로 초기화 후 다시 진행한다.
                cnt = 0;
            }
        }

        int[] result = new int[completeList.size()];

        for (int i=0; i<completeList.size(); i++) { //list에 있는 값 만큼 int 배열에 추가한다.
            result[i] = completeList.get(i);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 트럭 여러 대가 강을 가로지르는 일차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 다리에는 트럭이 최대 bridge_length대 올라갈 수 있으며,
     * 다리는 weight 이하까지의 무게를 견딜 수 있습니다. 단, 다리에 완전히 오르지 않은 트럭의 무게는 무시합니다.
     * 예를 들어, 트럭 2대가 올라갈 수 있고 무게를 10kg까지 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.
     * solution 함수의 매개변수로 다리에 올라갈 수 있는 트럭 수 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭 별 무게 truck_weights가 주어집니다. 이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.
     */
    @Test
    public void 다리를_지나는_트럭() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 19;
        int bridge_length = 5;
        int weight = 5;
        int[] truck_weights = {2, 2, 2, 2, 1, 1, 1, 1, 1};

        Queue<Integer> waitTruck = new LinkedList<>(); //대기중인 트럭
        Queue<Cross> crossTruck = new LinkedList<>(); //다리를 건너는 트럭

        for (int truck : truck_weights) { //트럭들을 대기 queue에 넣어준다.
            waitTruck.offer(truck);
        }

        int time = 0;

        while (!waitTruck.isEmpty() || !crossTruck.isEmpty()) { //대기중인 트럭이나 다리를 건너는 트럭이 하나라도 데이터가 있으면 반복
            int kg = 0;
            time++;

            //다리를 건너는 트럭 데이터가 있을때
            //현재까지 진행된 시간 >= 다리에 진입한 시간 + 다리를 건너는 소요 시간 일때 다리를 건넌것으로 판단하고 poll한다.
            if (!crossTruck.isEmpty() && time >= (bridge_length + crossTruck.peek().crossTime)) crossTruck.poll();

            if (waitTruck.peek() != null) { //대기중인 트럭이 있을때
                if (!crossTruck.isEmpty()) kg = crossTruck.stream().mapToInt(Cross::getKg).sum(); //다리를 건너는 트럭이 있을때 현재 건너는 트럭의 모든 무게를 kg에 대입해준다.
                if (kg + waitTruck.peek() <= weight) crossTruck.offer(new Cross(waitTruck.poll(), time)); //현재 건너는 트럭의 모든 무게 + 대기중인 트럭 무게를 합쳐서 다리의 최대 중량 weight 보다 작을때만 대기 중인 트럭을 poll해서 다리를 건너는 트럭으로 offer 해준다.
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(time));
    }

    private class Cross {
        int kg; //트럭의 무게
        int crossTime; //트럭이 진입한 시간

        public int getKg() {
            return this.kg;
        }

        Cross(int kg, int crossTime) {
            this.kg = kg;
            this.crossTime = crossTime;
        }
    }

    /**
     * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.
     * 예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.
     * 0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.
     */

    @Test
    public void 가장_큰_수() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "0";
        int[] numbers = {0, 0, 0, 0};
        List<Integer> collectList = new ArrayList<>();

        for (int number : numbers) { //정렬을 하기 위해 list에 숫자들을 넣어준다.
            collectList.add(number);
        }

        Collections.sort(collectList, (a, b) -> {
            String aStr = String.valueOf(a); //첫번째 인자값 a
            String bStr = String.valueOf(b); //두번째 인자값 b
            return -Integer.compare(Integer.parseInt(aStr + bStr), Integer.parseInt(bStr + aStr)); //a+b , b+a 값을 비교한다.
        });

        StringBuilder sb = new StringBuilder();

        for(Integer i : collectList) {
            sb.append(i);
        }

        String result = sb.toString().charAt(0) == '0' ? "0" : sb.toString(); //완성된 string에서 첫번째 값이 0 이면 0으로 치환하고 아닐때는 그대로 반환한다.

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 무인도에 갇힌 사람들을 구명보트를 이용하여 구출하려고 합니다. 구명보트는 작아서 한 번에 최대 2명씩 밖에 탈 수 없고, 무게 제한도 있습니다.
     * 예를 들어, 사람들의 몸무게가 [70kg, 50kg, 80kg, 50kg]이고 구명보트의 무게 제한이 100kg이라면 2번째 사람과 4번째 사람은 같이 탈 수 있지만
     * 1번째 사람과 3번째 사람의 무게의 합은 150kg이므로 구명보트의 무게 제한을 초과하여 같이 탈 수 없습니다.
     * 구명보트를 최대한 적게 사용하여 모든 사람을 구출하려고 합니다.
     * 사람들의 몸무게를 담은 배열 people과 구명보트의 무게 제한 limit가 매개변수로 주어질 때, 모든 사람을 구출하기 위해 필요한 구명보트 개수의 최솟값을 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 구명보트() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 3;
        int limit = 100;
        int[] people = {70, 80, 50};
        int result = 0;

        //기존에 풀었던 방식은 모든 요소가 모두 한개의 보트만 탈때는 오류가 발생했다.. 2개의 인자값을 비교해야하는데 어느조건에도 맞지 않아 무시되는 경우가 발생해서 생긴 문제였다.
        /*Integer peopleArr[] = Arrays.stream(people).boxed().toArray(Integer[]::new);
        Arrays.sort(peopleArr, Collections.reverseOrder());

        int temp = 0;

        for (int i=0; i<peopleArr.length; i++) {
            if (temp + peopleArr[i] >= limit) {
                result++;
                temp = peopleArr[i];
            } else {
                if (i == peopleArr.length) result++;
                temp += peopleArr[i];
            }
        }*/

        Arrays.sort(people); //먼저 people 배열을 정렬해준다. 기본인 오름차순으로 되어있다.

        Deque<Integer> deque = new ArrayDeque<>(people.length); //덱을 이용해서 양방향 큐의 기능을 동시에 이용하여 처리한다.
        for (int p : people) deque.add(p);

        while (!deque.isEmpty()) { //덱이 모두 추출되어 비어있을때까지 반복한다.
            int last = deque.pollLast(); //가장 먼저 마지막 요소를 poll 해서 덱에서 빼낸다.

            //덱의 마지막 요소를 빼냈기 때문에 덱이 비어있는 경우가 있어 isEmpty 체크를 해주고 아니라면 마지막 요소 + 가장 첫번째 요소를 참조한값이 limit 보다 작으면 첫번째 요소도 poll해서 빼낸다.
            //마지막 요소 + 가장 첫번째 참조 요소가 limit 보다 크면 둘이 탈 수 없기 때문에 첫번째 요소를 poll하지 않고 남겨둬서 마지막 요소 혼자 보트를 타게 처리된다.
            if (!deque.isEmpty() && last + deque.peekFirst() <= limit) deque.pollFirst();
            result++;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 어떤 숫자에서 k개의 수를 제거했을 때 얻을 수 있는 가장 큰 숫자를 구하려 합니다.
     * 예를 들어, 숫자 1924에서 수 두 개를 제거하면 [19, 12, 14, 92, 94, 24] 를 만들 수 있습니다. 이 중 가장 큰 숫자는 94 입니다.
     * 문자열 형식으로 숫자 number와 제거할 수의 개수 k가 solution 함수의 매개변수로 주어집니다.
     * number에서 k 개의 수를 제거했을 때 만들 수 있는 수 중 가장 큰 숫자를 문자열 형태로 return 하도록 solution 함수를 완성하세요.
     */
    String maxNum = "0";

    @Test
    public void 큰_수_만들기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "3234";
        String number = "1231234";
        int k = 3;
        String[] reComArr = new String[number.length() - k];
        StringBuilder sb = new StringBuilder();

        //중복조합을 통해서 모든 수를 구한 후에 풀면 풀이는 어느정도 가능하지만 비정상적으로 큰 수가 들어오는 경우 런타임 에러가 발생한다..
        combination(number.split(""), number.length(), number.length() - k, reComArr, sb);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(maxNum));
    }

    private void combination(String[] number, int n, int r, String[] reComArr, StringBuilder sb) {
        if(r == 0) {
            for (String num : reComArr) sb.append(num);

            if (Integer.parseInt(maxNum) < Integer.parseInt(sb.toString())) maxNum = sb.toString();
            sb.setLength(0);
            return;
        } else if(n < r) {
            return;
        } else {
            reComArr[r-1] = number[n-1];
            combination(number, n-1, r-1, reComArr, sb);
            combination(number, n-1, r, reComArr, sb);
        }
    }

    @Test
    public void 큰_수_만들기_v2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "3234";
        String number = "1231234";
        String[] num = number.split("");
        int k = 3;
        int numberLength = number.length();
        int limitLength = numberLength - k;

        StringBuilder sb = new StringBuilder();
        List<Integer> list = new ArrayList<>();
        int jNo = -1;

        //기존 방식으로 풀었을때는 런타임 에러가 발생해서 다른 방법으로 풀었다.
        //제공받은 number에서 k만큼 숫자를 왼쪽에서부터 하나씩 제거했을때 가장 큰 숫자를 구해야하기때문에 우선 number.length만큼 for문을 반복하면서 처리한다.
        for (int i=0; i<limitLength; i++) {
            int val = 0; //시작 비교를 위해 0을 초기값으로 정해준다.

            //시작점은 jNo에 현재값이 들어오기때문에 +1을해서 다음 숫자부터 탐색
            //종료는 7 - (4 - 0) + 1 해서 list에 사이즈가 증가할때마다 점점 영역이 늘어나게되서 jNo가 증가하기 때문에 같이 증가하게 된다.
            for (int j=jNo+1; j<number.length() - (limitLength - list.size()) + 1; j++) {
                //비교대상값보다 현재 값이 클 경우
                if (val < Integer.parseInt(num[j])) {
                    jNo = j; //2차 for문 시작점으로 지정하고
                    val = Integer.parseInt(num[j]); //val에 해당 값을 넣어준다.
                    if (val == 9) break; //만약 해당값이 9일경우 무조건 큰 수이기때문에 더이상 비교할 필요가 없어 for문을 벗어난다.
                }
            }

            list.add(val); //큰 수가 추출되면 list에 넣어준다.
        }

        for (int no : list) sb.append(no);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(sb.toString()));
    }

    /**
     * 전화번호부에 적힌 전화번호 중, 한 번호가 다른 번호의 접두어인 경우가 있는지 확인하려 합니다.
     * 전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.
     * 구조대 : 119
     * 박준영 : 97 674 223
     * 지영석 : 11 9552 4421
     * 전화번호부에 적힌 전화번호를 담은 배열 phone_book 이 solution 함수의 매개변수로 주어질 때, 어떤 번호가 다른 번호의 접두어인 경우가 있으면 false를 그렇지 않으면 true를 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 전화번호_목록() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final boolean answer = false;
        boolean result = true;
        String[] phone_book = {"119", "97674223", "1195524421"};

        /* 해당문제는 value가 딱히 필요없어서 hashMap을 이용할 필요는 없지만 hashset이 조금 더 속도가 느린듯한 느낌이다.
        HashMap<Integer, String> phoneMap = new HashMap<>();

        for (String phone : phone_book) {
            phoneMap.put(Integer.parseInt(phone), phone);
        }

        Object[] mapKey = phoneMap.keySet().toArray();
        Arrays.sort(mapKey);

        for (Map.Entry<Integer, String> entry : phoneMap.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }*/

        //stream을 이용해서 별도의 for문 없이 collect 속성을 이용해 hashset 데이터를 바로 세팅했다
        HashSet<String> hashSet = (HashSet<String>) Arrays.stream(phone_book).collect(Collectors.toSet());

        for (String key : hashSet) { //전체 hashset 숫자만큼 for문을 돈다
            for (int j = 1; j < key.length(); j++) { //무조건 0부터 substring을 해야하기때문에 j는 1부터 시작하고 전체 길이까지 반복한다
                if (hashSet.contains(key.substring(0, j))){ //key.substring으로 하나씩 증가하면서 검색했을때 contains로 hashset내에 존재하는 자료인지 확인한다
                    result = false; //0부터 시작하기 때문에 string 클래스의 startWith처럼 작동해서 확인이 가능해서 있으면 false로 입력된다
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 양의 정수 x에 대한 함수 f(x)를 다음과 같이 정의합니다.
     * x보다 크고 x와 비트가 1~2개 다른 수들 중에서 제일 작은 수
     * 예를 들어,
     * f(2) = 3 입니다. 다음 표와 같이 2보다 큰 수들 중에서 비트가 다른 지점이 2개 이하이면서 제일 작은 수가 3이기 때문입니다.
     * f(7) = 11 입니다. 다음 표와 같이 7보다 큰 수들 중에서 비트가 다른 지점이 2개 이하이면서 제일 작은 수가 11이기 때문입니다.
     * 정수들이 담긴 배열 numbers가 매개변수로 주어집니다. numbers의 모든 수들에 대하여 각 수의 f 값을 배열에 차례대로 담아 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void two개_이하로_다른_비트() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final long[] answer = {3L, 11L};
        long[] numbers = {2, 7};
        long[] result = new long[numbers.length];

        for (int i=0; i<numbers.length; i++) {
            if (numbers[i] % 2 == 0) { //짝수면 + 1만 진행 하면된다. 짝수의 2진수는 모두 0으로 끝나기 때문에 끝을 1로 바꾸는 것 만으로 해결이 가능하다.
                result[i] = numbers[i] + 1;
            } else { //홀수일때
                StringBuilder temp = new StringBuilder();
                String binaryString = Long.toBinaryString(numbers[i]);

                if (!binaryString.contains("0")) { //0을 미포함하는 경우 즉 1로만 이루어진 경우 앞에서 2번째 자리에 0을 삽입하여 처리한다.
                    temp.append("10");
                    temp.append(binaryString.substring(1).replace("0", "1"));
                } else { //0을 포함하는 경우 마지막 0을 1로 바꾸고 그 뒤에 1을 0으로 바꿔서 처리한다.
                    int lastIndex = binaryString.lastIndexOf("0");
                    int firstOneIndex = binaryString.indexOf("1", lastIndex);

                    temp.append(binaryString, 0, lastIndex).append("1");
                    temp.append("0");
                    temp.append(binaryString.substring(firstOneIndex + 1));
                }

                result[i] = Long.parseLong(temp.toString(), 2);
            }
        }

        /*
        다른사람의 풀이를 가져와봤는데.. 쉬프트연산을 통해 문제를 해결했다.. 확실히 이런 방법을 통해서도 가능하다는걸 새삼 깨닫게 된다.
        long[] answer = numbers.clone();
        for(int i = 0; i< answer.length; i++){
            answer[i]++; //answer+1 해준다.
            answer[i] += (answer[i]^numbers[i])>>>2; // xor 연산자를 통해 answer+1 과 answer 값을 비교해서 수가 같게 만들때 쉬피트로 2개까지 민다.. 뭐 이런 뜻이라고 하는데 솔직히 무슨말인지 모르겠다;;
        }
         */

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     *H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과1에 따르면, H-Index는 다음과 같이 구합니다.
     * 어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.
     * 어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void H_Index() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 10;
        int[] citations = {8,10,10,15,17,22,24,28,32,42,47};
        int result = 0;

        Integer newArr[] = Arrays.stream(citations).boxed().toArray(Integer[]::new); //int는 primitive기 때문에 배열에서 내림차순으로 정렬이 안되기 때문에 Integer로 변환한다.
        Arrays.sort(newArr, Collections.reverseOrder()); //Integer로 변환된 배열을 내림차순으로 정렬한다.

        //내림차순으로 정렬된 배열의 크기만큼 반복한다.
        for (int i=0; i<newArr.length; i++) {
            //index가 newArr.length보다 작거나 같으며 (length를 초과한 index는 존재할 수 없기 때문) -> 근데 이 조건은 필요없었다. 있으나 마나 한 쓰레기 조건임.
            //index값이 for문을 돌면서 내림차순 배열 값보다 크거나 같을 경우에 최대 hIndex라 판단하여 for문을 종료한다.
            //if (result <= newArr.length && result >= newArr[i]) break;
            if (result >= newArr[i]) break;
            result++;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 스파이들은 매일 다른 옷을 조합하여 입어 자신을 위장합니다.
     * 예를 들어 스파이가 가진 옷이 아래와 같고 오늘 스파이가 동그란 안경, 긴 코트, 파란색 티셔츠를 입었다면 다음날은 청바지를 추가로 입거나 동그란 안경 대신 검정 선글라스를 착용하거나 해야 합니다.
     *
     * 종류	이름
     * 얼굴	동그란 안경, 검정 선글라스
     * 상의	파란색 티셔츠
     * 하의	청바지
     * 겉옷	긴 코트
     * 스파이가 가진 의상들이 담긴 2차원 배열 clothes가 주어질 때 서로 다른 옷의 조합의 수를 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 위장() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 5;
        String[][] clothes = {{"yellowhat", "headgear"}, {"bluesunglasses", "eyewear"}, {"green_turban", "headgear"}};
        int result = 1;

        HashMap<String, Integer> clotheMap = new HashMap<>();

        for (int i=0; i<clothes.length; i++) {
            //hashMap을 통해 옷의 종류당 몇개의 옷을 가지고 있는지 처리해준다.
            //옷의 종류를 key로 잡고 getOrDefault를 통해 해당 키와 동일한 옷이 있으면 count를 늘려서 value에 넣어준다.
            clotheMap.put(clothes[i][1], clotheMap.getOrDefault(clothes[i][1], 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : clotheMap.entrySet()) {
            //옷의 종류에 해당하는 갯수만큼 처리해야 하기 때문에
            //headgear는 2개, eyewear는 1개 여서 수식으로 전환하면 (2+1)*(1+1)-1 로 확인할 수 있다.
            result *= entry.getValue() + 1;
        }

        //문제에서는 꼭 한개의 옷이라도 입어야하는데 모두 안입는 경우에 대한 수가 1이 있기때문에 결과값에서 -1을 해서 모두 안입는 경우의 수에 대응해준다.
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result - 1));
    }

    /**
     * n개의 음이 아닌 정수가 있습니다. 이 수를 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다. 예를 들어 [1, 1, 1, 1, 1]로 숫자 3을 만들려면 다음 다섯 방법을 쓸 수 있습니다.
     * -1+1+1+1+1 = 3
     * +1-1+1+1+1 = 3
     * +1+1-1+1+1 = 3
     * +1+1+1-1+1 = 3
     * +1+1+1+1-1 = 3
     * 사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 타겟_넘버() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 5;
        int[] numbers = {1, 1, 1, 1, 1};
        int target = 3;

        int result = dfs(numbers,0,0, target);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    //탐색은 항상 어렵다.. 재귀호출에 대한 이해도가 떨어지는듯 하다.
    private int dfs(int[] numbers, int depth, int sum, int target) {
        if (depth != numbers.length) {
            int plus = dfs(numbers,depth + 1,sum + numbers[depth], target);
            int minus = dfs(numbers,depth + 1,sum - numbers[depth], target);
            System.out.println(sum + "=" + plus + " , " + minus);

            return plus + minus;
        }

        if (target != sum) return 0;

        return 1;
    }

    /**
     * 1부터 n까지 번호가 붙어있는 n명의 사람이 영어 끝말잇기를 하고 있습니다. 영어 끝말잇기는 다음과 같은 규칙으로 진행됩니다.
     *
     * 1.1번부터 번호 순서대로 한 사람씩 차례대로 단어를 말합니다.
     * 2.마지막 사람이 단어를 말한 다음에는 다시 1번부터 시작합니다.
     * 3.앞사람이 말한 단어의 마지막 문자로 시작하는 단어를 말해야 합니다.
     * 4.이전에 등장했던 단어는 사용할 수 없습니다.
     * 5.한 글자인 단어는 인정되지 않습니다.
     * 다음은 3명이 끝말잇기를 하는 상황을 나타냅니다.
     *
     * tank → kick → know → wheel → land → dream → mother → robot → tank
     *
     * 위 끝말잇기는 다음과 같이 진행됩니다.
     *
     * 1번 사람이 자신의 첫 번째 차례에 tank를 말합니다.
     * 2번 사람이 자신의 첫 번째 차례에 kick을 말합니다.
     * 3번 사람이 자신의 첫 번째 차례에 know를 말합니다.
     * 1번 사람이 자신의 두 번째 차례에 wheel을 말합니다.
     * (계속 진행)
     * 끝말잇기를 계속 진행해 나가다 보면, 3번 사람이 자신의 세 번째 차례에 말한 tank 라는 단어는 이전에 등장했던 단어이므로 탈락하게 됩니다.
     *
     * 사람의 수 n과 사람들이 순서대로 말한 단어 words 가 매개변수로 주어질 때, 가장 먼저 탈락하는 사람의 번호와 그 사람이 자신의 몇 번째 차례에 탈락하는지를 구해서 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 영어_끝말잇기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {0, 0};
        String[] words = {"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"};
        int n = 5;
        int[] result = new int[2];

        List<EndTalkData> list = new ArrayList<>();
        int seq = 0;
        boolean endCheck = true;

        for (int i=0; i<words.length; i++) {
            if (i % n == 0) seq++; //n명의 사람에 맞춰 순서를 늘려준다.

            EndTalkData talkData = new EndTalkData((i % n) + 1, words[i]); //번호는 1부터 시작하도록 +1을 해준다.
            list.add(talkData);

            if (i == 0) continue; //0번째는 데이터만 쌓고 무시하도록 설계

            String iWord = list.get(i).getWord();
            String beforeWord = list.get(i-1).getWord();
            int iUserNo = list.get(i).getUserNo();

            //첫번째 문장의 마지막 글자와 두번째 문장의 첫글자가 일치하는지 확인, 일치하지 않으면 result[0]에 user의 번호, result[1]에 현재 진행 seq를 넣어서 for를 종료한다.
            if (!beforeWord.substring(beforeWord.length() - 1).equals(iWord.substring(0, 1))) {
                result[0] = iUserNo;
                result[1] = seq;
                endCheck = false;
                break;
            }

            int index = 0;

            //여태 참가자들이 말한 word가 list에 쌓이고 그 안에서 같은단어를 사용한적이 있는지 확인
            //EndTalkData class를 사용했기 때문에 indexOf를 쓸수 없어서 for문으로 data를 가져와서 직접 비교하는 방식으로 구현
            for (int l=0; l<list.size(); l++) {
                EndTalkData getData = list.get(l);

                //이미 말했던 word가 존재한다면 발견된 index값을 세팅해서 for문을 종료한다.
                if (getData.getWord().equals(iWord)) {
                    index = l;
                    break;
                }
            }

            //만약 일치했을 경우 중복단어 여부 확인
            if (index > -1 && list.get(index).getUserNo() != iUserNo) {
                result[0] = iUserNo;
                result[1] = seq;
                endCheck = false;
                break;
            }
        }

        //모든 단어가 완벽하게 끝나면 틀린 데이터가 없기 때문에 {0, 0}으로 세팅해서 끝낸다.
        if (endCheck) {
            result[0] = 0;
            result[1] = 0;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    private class EndTalkData {
        int userNo;
        String word;

        public int getUserNo() {
            return userNo;
        }

        public String getWord() {
            return word;
        }

        EndTalkData(int userNo, String word) {
            this.userNo = userNo;
            this.word = word;
        }
    }

    @Test
    public void 영어_끝말잇기_v2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {0, 0};
        String[] words = {"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"};
        int n = 5;
        int[] result = {0, 0};

        List<String> list = new ArrayList<>();
        list.add(words[0]); //첫 시작 값은 기본으로 넣어놓고 시작한다.
        int seq = 1;

        //원래 풀었던 방법은 dto를 활용해 userNo, word를 같이 기록해서 풀었는데 굳이 어렵게 같이 기록할 필요 없이 하나의 for문 안에서 words의 String만으로 처리할 수 있었다.
        for (int i=1; i<words.length; i++) {
            int userNo = i % n; //userNo를 구하기 위해 i % n을 해주었다.

            if (userNo == 0) {
                seq++; //자신의 순서가 다시 돌아왔을때 한 사이클을 돌았으므로 seq를 늘려준다.
            }

            //이전단어의 마지막 char와 현재단어의 첫번째 char을 비교해서 같지 않으면 끝말잇기 규칙이 성립하지 않는다.
            //또 현재단어가 이전단어들이 들어가있는 list에서 검색되었을 경우 같은 단어를 반복했기 때문에 이경우에도 실패로 간주한다.
            //원래라면 5번 규칙인 한글자 단어도 걸러야하는데.. 테스트케이스에는 한글자인 단어가 없었나보다. 허술한 규칙인듯
            if (!list.get(i - 1).substring(list.get(i - 1).length() - 1).equals(words[i].substring(0, 1))
                    || list.contains(words[i])) {
                result[0] = userNo + 1; //배열의 구성상 0부터 시작하지만 문제에서는 순서를 1부터 시작하기 떄문에 +1을 해준다.
                result[1] = seq; //현재까지 몇사이클 돌았는지 확인하고 몇번째 순서에서 틀렸는지 기록한다.
                break; //틀린 경우 break를 통해 for문을 종료한다.
            }

            list.add(words[i]); //위에 끝말잇기 규칙에 어긋나지 않은 데이터는 이전단어들이 들어가있는 list에 넣어준다.
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 땅따먹기 게임을 하려고 합니다. 땅따먹기 게임의 땅(land)은 총 N행 4열로 이루어져 있고, 모든 칸에는 점수가 쓰여 있습니다.
     * 1행부터 땅을 밟으며 한 행씩 내려올 때, 각 행의 4칸 중 한 칸만 밟으면서 내려와야 합니다.
     * 단, 땅따먹기 게임에는 한 행씩 내려올 때, 같은 열을 연속해서 밟을 수 없는 특수 규칙이 있습니다.
     *
     * 예를 들면,
     * | 1 | 2 | 3 | 5 |
     * | 5 | 6 | 7 | 8 |
     * | 4 | 3 | 2 | 1 |
     * 로 땅이 주어졌다면, 1행에서 네번째 칸 (5)를 밟았으면, 2행의 네번째 칸 (8)은 밟을 수 없습니다.
     * 마지막 행까지 모두 내려왔을 때, 얻을 수 있는 점수의 최대값을 return하는 solution 함수를 완성해 주세요.
     * 위 예의 경우, 1행의 네번째 칸 (5), 2행의 세번째 칸 (7), 3행의 첫번째 칸 (4) 땅을 밟아 16점이 최고점이 되므로 16을 return 하면 됩니다.
     */
    @Test
    public void 땅따먹기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 16;
        int[][] land = {{1,2,3,5}, {5,6,7,8}, {4,3,2,1}};

        //문제 인식을 잘못했다. 이렇게 풀면 시간초과를 유발해서 DP를 이용해 풀어야한다.
        /*
        int seq = -1;
        int max = 0;

        for (int i=0; i<land.length; i++) {
            for (int j=0; j<land[i].length; j++) {
                //max = Math.max(max, land[i][j]);
                if (max < land[i][j]) {
                    max = land[i][j];
                    seq = j;
                }

                //마지막 단계일때 계산 처리
                if ((land[i].length - 1) == j) {
                    System.out.println(max);
                    max = 0;
                }
            }
        }*/

        //첫번째 열을 제외한 2번째 열부터 계산을 시작한다.
        for(int i=1; i<land.length; i++) {
            land[i][0] += max(land[i-1][1], land[i-1][2], land[i-1][3]); //기준이 되는 [i][0]에서 두번째 인자값이 0이기 때문에 0이 아닌 나머지를 매칭 해준다. 1,2,3
            land[i][1] += max(land[i-1][0], land[i-1][2], land[i-1][3]); //두번째 인자값이 1이기 때문에 0,2,3
            land[i][2] += max(land[i-1][0], land[i-1][1], land[i-1][3]); //두번째 인자값이 2이기 때문에 0,2,3
            land[i][3] += max(land[i-1][0], land[i-1][1], land[i-1][2]); //두번째 인자값이 3이기 때문에 0,1,2
            //배열의 자기 자신의 열을 처리할때마다 land[i-1] 즉 이전 행의 데이터를 가져와서 겹치지 않는 조건하에 더했을때의 가장 큰수를 계산하고 그 값을 다시 계산한 자신의 i번째 열에다가 대입해서
            //다음 계산시에 이미 더해진 값을 기준으로 다시 판단할 수 있도록 한다.
            //자칫 중복될 수 있는 처리를 지정을 해서 불필요한 반복이 이뤄지지 않고 더 빠르게 찾을 수 있게 함수의 계산값을 저장(memoization)하여 처리하는것을 동적 프로그래밍(Dynamic Programming) 처리라고 한다.
        }

        int max = 0;
        //각 열에서 저장된값이 가장 마지막 열에 포함되어 있기 때문에 land[land.length-1]을 하여 land[3]열에 있는 데이터 중 가장 큰 수를 골라내서 값에 대입해준다.
        for (int no : land[land.length-1]) {
            max = Math.max(max, no);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(max));
    }

    //3개의 숫자 중 가장 큰 값을 찾아야하기 때문에 별도로 max 함수를 빼서 작업
    private int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    /**
     * 프렌즈대학교 컴퓨터공학과 조교인 제이지는 네오 학과장님의 지시로, 학생들의 인적사항을 정리하는 업무를 담당하게 되었다.
     * 그의 학부 시절 프로그래밍 경험을 되살려, 모든 인적사항을 데이터베이스에 넣기로 하였고, 이를 위해 정리를 하던 중에 후보키(Candidate Key)에 대한 고민이 필요하게 되었다.
     * 후보키에 대한 내용이 잘 기억나지 않던 제이지는, 정확한 내용을 파악하기 위해 데이터베이스 관련 서적을 확인하여 아래와 같은 내용을 확인하였다.
     *
     * 관계 데이터베이스에서 릴레이션(Relation)의 튜플(Tuple)을 유일하게 식별할 수 있는 속성(Attribute) 또는 속성의 집합 중, 다음 두 성질을 만족하는 것을 후보 키(Candidate Key)라고 한다.
     * 유일성(uniqueness) : 릴레이션에 있는 모든 튜플에 대해 유일하게 식별되어야 한다.
     * 최소성(minimality) : 유일성을 가진 키를 구성하는 속성(Attribute) 중 하나라도 제외하는 경우 유일성이 깨지는 것을 의미한다. 즉, 릴레이션의 모든 튜플을 유일하게 식별하는 데 꼭 필요한 속성들로만 구성되어야 한다.
     * 제이지를 위해, 아래와 같은 학생들의 인적사항이 주어졌을 때, 후보 키의 최대 개수를 구하라.     *
     * 위의 예를 설명하면, 학생의 인적사항 릴레이션에서 모든 학생은 각자 유일한 "학번"을 가지고 있다. 따라서 "학번"은 릴레이션의 후보 키가 될 수 있다.
     * 그다음 "이름"에 대해서는 같은 이름("apeach")을 사용하는 학생이 있기 때문에, "이름"은 후보 키가 될 수 없다. 그러나, 만약 ["이름", "전공"]을 함께 사용한다면 릴레이션의 모든 튜플을 유일하게 식별 가능하므로 후보 키가 될 수 있게 된다.
     * 물론 ["이름", "전공", "학년"]을 함께 사용해도 릴레이션의 모든 튜플을 유일하게 식별할 수 있지만, 최소성을 만족하지 못하기 때문에 후보 키가 될 수 없다.
     * 따라서, 위의 학생 인적사항의 후보키는 "학번", ["이름", "전공"] 두 개가 된다.
     *
     * 릴레이션을 나타내는 문자열 배열 relation이 매개변수로 주어질 때, 이 릴레이션에서 후보 키의 개수를 return 하도록 solution 함수를 완성하라.
     */
    List<Integer> key = new ArrayList<>();

    boolean ckMinimality(int i) {
        for (int k : key)
            if ((k & i) == k) return false;
        return true;
    }

    @Test
    public void 후보키() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 2;
        int result = 0;
        String[][] relation = {{"100","ryan","music","2"}, {"200","apeach","math","2"}, {"300","tube","computer","3"}
                , {"400","con","computer","4"}, {"500","muzi","music","3"}, {"600","apeach","music","2"}};

        /*HashSet<String> hashSet = new HashSet<>();

        for (int i=0; i<relation.length; i++) {
            String r = "";

            for (int j=0; j<relation[i].length; j++) {
                r += relation[i][j];
                System.out.println(r + " ");
                hashSet.add(r);
            }

            r = null;
            System.out.println();
        }*/

        int n = relation.length;
        int m = relation[0].length;

        //시프트 연산을 통한 문제 풀이
        for (int k = 1; k < (1 << m); k++) {
            if (!ckMinimality(k)) continue;

            Set<String> set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                StringBuilder key = new StringBuilder();

                for (int j = 0; j < m; j++) {
                    if (((1 << j) & k) > 0) key.append(relation[i][j]).append('/');
                }

                if (!set.add(key.toString())) break;
            }
            if (set.size() == n) key.add(k);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(key.size()));
    }

    /**
     * Leo는 카펫을 사러 갔다가 아래 그림과 같이 중앙에는 노란색으로 칠해져 있고 테두리 1줄은 갈색으로 칠해져 있는 격자 모양 카펫을 봤습니다.
     * Leo는 집으로 돌아와서 아까 본 카펫의 노란색과 갈색으로 색칠된 격자의 개수는 기억했지만, 전체 카펫의 크기는 기억하지 못했습니다.
     * Leo가 본 카펫에서 갈색 격자의 수 brown, 노란색 격자의 수 yellow가 매개변수로 주어질 때 카펫의 가로, 세로 크기를 순서대로 배열에 담아 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 카펫() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {8, 6};
        int[] result = new int[2];
        int brown = 24;
        int yellow = 24;
        int sum = brown + yellow;

        //yellow격자를 둘러싼 테두리가 생성되기 위해서는 퇴소 3부터 시작해야한다.
        //격자의 전체 면적을 확인해야하기 때문에 전체면적은 brown + yellow 만큼 확인한다.
        for (int i=3; i<sum; i++) {

            //카펫의 전체 면적에 대한 가로세로 약수 쌍을 구한다.
            if (sum % i == 0) {
                int outCol = sum / i; //brown 테두리의 가로
                int outRow = sum / outCol; //brown 테두리의 세로

                int inCol = outCol - 2; //yellow 면적의 가로
                int inRow = outRow - 2; //yellow 면적의 세로

                //yellow의 가로세로 길이를 곱했을때 yellow와 같고
                //문제에 카펫의 가로 >= 세로 조건이 있기 때문에 && 조건으로 같이 처리한다.
                if ((inCol * inRow) == yellow && outCol >= outRow) {
                    result[0] = outCol;
                    result[1] = outRow;
                    break;
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 카카오에 신입 개발자로 입사한 "콘"은 선배 개발자로부터 개발역량 강화를 위해 다른 개발자가 작성한 소스 코드를 분석하여 문제점을 발견하고 수정하라는 업무 과제를 받았습니다. 소스를 컴파일하여 로그를 보니 대부분 소스 코드 내 작성된 괄호가 개수는 맞지만 짝이 맞지 않은 형태로 작성되어 오류가 나는 것을 알게 되었습니다.
     * 수정해야 할 소스 파일이 너무 많아서 고민하던 "콘"은 소스 코드에 작성된 모든 괄호를 뽑아서 올바른 순서대로 배치된 괄호 문자열을 알려주는 프로그램을 다음과 같이 개발하려고 합니다.
     *
     * '(' 와 ')' 로만 이루어진 문자열이 있을 경우, '(' 의 개수와 ')' 의 개수가 같다면 이를 균형잡힌 괄호 문자열이라고 부릅니다.
     * 그리고 여기에 '('와 ')'의 괄호의 짝도 모두 맞을 경우에는 이를 올바른 괄호 문자열이라고 부릅니다.
     * 예를 들어, "(()))("와 같은 문자열은 "균형잡힌 괄호 문자열" 이지만 "올바른 괄호 문자열"은 아닙니다.
     * 반면에 "(())()"와 같은 문자열은 "균형잡힌 괄호 문자열" 이면서 동시에 "올바른 괄호 문자열" 입니다.
     * '(' 와 ')' 로만 이루어진 문자열 w가 "균형잡힌 괄호 문자열" 이라면 다음과 같은 과정을 통해 "올바른 괄호 문자열"로 변환할 수 있습니다.
     *
     * 1. 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다.
     * 2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다. 단, u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있습니다.
     * 3. 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다.
     *   3-1. 수행한 결과 문자열을 u에 이어 붙인 후 반환합니다.
     * 4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정을 수행합니다.
     *   4-1. 빈 문자열에 첫 번째 문자로 '('를 붙입니다.
     *   4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙입니다.
     *   4-3. ')'를 다시 붙입니다.
     *   4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙입니다.
     *   4-5. 생성된 문자열을 반환합니다.
     */
    @Test
    public void 괄호_변환() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "()(())()";
        String p = "()))((()";
        String result = sol(p);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    public static String sol(String w) {
        if (w.equals("")) return "";

        String u = w.substring(0, B_check(w));
        String v = w.substring(B_check(w), w.length());
        return (check(u)) ? u + sol(v) : '(' + sol(v) + ')' + change(u);
    }

    //올바른 문장인지 판단한다.
    public static Boolean check(String str) {
        int ch = 0;

        for (int i=0; i<str.length(); i++) {
            // (이면 1 )이면 -1을 하여 전체 문장의 숫자를 판단한다.
            ch += (str.charAt(i) == '(') ? 1 : -1;
            // 0보다 작은 경우 옳지 않은 문장이라 판단하여 false를 리턴한다.
            if(ch < 0) return false;
        }

        //0이 아닐경우에도 옳지 않기 떄문에 false를 리턴한다.
        if (ch != 0) return false;

        return true;
    }

    //주어진 str 만큼 반복하며 전체 str의 올바른 문장인지 판단한다.
    public static int B_check(String str) {
        int ch = 0;

        for (int i=0; i<str.length(); i++) {
            // (이면 1 )이면 -1을 하여 전체 문장의 숫자를 판단한다.
            ch += (str.charAt(i) == '(') ? 1 : -1;
            //0이 되었을때 i에 1을 더해서 리턴한다.
            if (ch == 0) return i + 1;
        }

        //기본은 0으로 리턴한다.
        return 0;
    }

    //주어진 str을 모두 반대 괄호로 변경한다.
    public static String change(String str) {
        String s = "";

        for (int i=1; i<str.length()-1; i++) {
            s += (str.charAt(i) == '(') ? ')' : '(';
        }

        return s;
    }
}