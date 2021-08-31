package codingtest.fancizCodingTest.programmers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class LevelOneTest {

    /**
     * 프로그래머스 모바일은 개인정보 보호를 위해 고지서를 보낼 때 고객들의 전화번호의 일부를 가립니다.
     * 전화번호가 문자열 phone_number로 주어졌을 때, 전화번호의 뒷 4자리를 제외한 나머지 숫자를 전부 *으로 가린 문자열을 리턴하는 함수, solution을 완성해주세요.
     */
    @Test
    public void 핸드폰번호가리기() {
        final String answer = "*******4444";
        /*
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<phone_number.length()-4; i++) {
            sb.append("*");
        }

        sb.append(phone_number.substring(phone_number.length()-4, phone_number.length()));
        return sb.toString();

        기존 해결 방법에서 stream을 이용해서 해봤는데 속도는 더 느려지고
        stream 내부에서 자체 index를 활용하기 어려워서 외부 index 배열의 값으로 판단했다.

        과연 stream으로 표현한 코드가 더 이쁘고 깔끔한것인가에 대해서는 많은 고민이 필요할것같다.
        성능은 확실히 떨어진다..
        */
        String phone_number = "01033334444";
        int[] index = {0};

        String result = Arrays.stream(phone_number.split(""))
                .map(s -> (index[0]++ < phone_number.length()-4) ? "*" : s)
                .collect(Collectors.joining(""));

        assertThat(answer, is(result));
    }

    /**
     * 배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.
     * 예를 들어 array가 [1, 5, 2, 6, 3, 7, 4], i = 2, j = 5, k = 3이라면 array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.
     * 1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
     * 2에서 나온 배열의 3번째 숫자는 5입니다.
     */
    @Test
    public void K번째수() {
        final int[] answer = {5, 6, 3};
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};
        int[] result = new int[commands.length];

        for(int a=0; a<commands.length; a++) {
            result[a] = arrayProc(array, commands, a);
        }

        assertThat(answer, is(result));
    }

    private int arrayProc(int[] array, int[][] commands, int index) {
        int[] sortArray = Arrays.copyOfRange(array, commands[index][0]-1, commands[index][1]);
        Arrays.sort(sortArray);

        return sortArray[commands[index][2]-1];
    }

    /**
     * 문자열 s에 나타나는 문자를 큰것부터 작은 순으로 정렬해 새로운 문자열을 리턴하는 함수, solution을 완성해주세요.
     * s는 영문 대소문자로만 구성되어 있으며, 대문자는 소문자보다 작은 것으로 간주합니다.
     */
    @Test
    public void 내림차순문자열() {
        final String answer = "gfedcbZ";
        String s = "Zbcdefg";
        String[] strArray = s.split("");

        Arrays.sort(strArray, Collections.reverseOrder());

        StringBuilder sb = new StringBuilder();
        for(String str : strArray) sb.append(str);

        assertThat(answer, is(sb.toString()));
    }

    /**
     * 두 정수 a, b가 주어졌을 때 a와 b 사이에 속한 모든 정수의 합을 리턴하는 함수, solution을 완성하세요.
     * 예를 들어 a = 3, b = 5인 경우, 3 + 4 + 5 = 12이므로 12를 리턴합니다.
     */
    @Test
    public void 두정수사이의합() {
        final long answer = 12;
        int a = 3;
        int b = 5;

        long result = 0;

        if(a > b) {
            result = LongStream.range(b, a+1).reduce(0, Long::sum);
        } else {
            result = LongStream.range(a, b+1).reduce(0, Long::sum);
        }

        assertThat(answer, is(result));
    }

    /**
     * 정수를 저장한 배열, arr 에서 가장 작은 수를 제거한 배열을 리턴하는 함수, solution을 완성해주세요. 단, 리턴하려는 배열이 빈 배열인 경우엔 배열에 -1을 채워 리턴하세요.
     * 예를들어 arr이 [4,3,2,1]인 경우는 [4,3,2]를 리턴 하고, [10]면 [-1]을 리턴 합니다.
     */
    @Test
    public void 제일작은수제거() {
        /*
        문제에 함정이었던건지 내가 잘못생각한건지 모르겠지만 처음에 삽입정렬로 내림차순으로 정렬하여 마지막 배열 요소를 제거하려는 식으로 코드를 짰는데
        답안 제출시 전부 실패하길래 이상하다 생각했는데 문제의 함정이었나보다..
        제시해주는 arr을 받아서 리턴할때 정렬이 된 형태로 리턴하는것이 아닌 정말 제일 작은 수만 제거한 형태의 그대로 리턴을 해야하는것이었다.
        따라서 정렬했던 로직을 제외시키고 Collections.min을 이용해 제일 작은 수를 찾은 다음 ArrayList의 remove를 이용해서 통과하였다.
         */
        final int[] answer = {4,3,2};
        int[] arr = {4,3,2,1};
        int[] result;

        if(arr.length <= 1 || arr[0] == 10) {
            result = new int[1];
            result[0] = -1;
            return;
        }

        ArrayList<Integer> list = new ArrayList();

        for(int i=0; i<arr.length; i++) {
            list.add(arr[i]);
        }

        list.remove(Integer.valueOf(Collections.min(list)));

        result = new int[list.size()];
        for(int x=0; x<list.size(); x++) {
            result[x] = list.get(x);
        }

        assertThat(answer, is(result));
    }

    /**
     * 함수 solution은 정수 x와 자연수 n을 입력 받아, x부터 시작해 x씩 증가하는 숫자를 n개 지니는 리스트를 리턴해야 합니다. 다음 제한 조건을 보고, 조건을 만족하는 함수, solution을 완성해주세요.
     */
    @Test
    public void X만큼간격이있는N개의숫자() {
        final long[] answer = {2L,4L,6L,8L,10L};
        int x = 2;
        int n = 5;
        long[] result = new long[n];
        int value = 0;

        /*
        List<Long> list = LongStream.range(0, n)
                .map(s -> (s+1) * x)
                .boxed()
                .collect(Collectors.toList());

        for (Long t : list) {
            System.out.println(t);
        }

        stream으로 변환해서 풀어봤을때 정답은 나오지만 역시나 속도가 현저히 느리고 list를 array로 한번 더 변환해야하는 불편한 점이 있다.
         */

        for(int i=0; i<n; i++) {
            result[i] = (value += x);
        }

        assertThat(answer, is(result));
    }

    /**
     * 이 문제에는 표준 입력으로 두 개의 정수 n과 m이 주어집니다.
     * 별(*) 문자를 이용해 가로의 길이가 n, 세로의 길이가 m인 직사각형 형태를 출력해보세요.
     */
    @Test
    public void 직사각형별찍기() {
        int a = 5;
        int b = 3;

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<b; i++) {
            for(int j=0; j<a; j++) {
                sb.append("*");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    /**
     * 행렬의 덧셈은 행과 열의 크기가 같은 두 행렬의 같은 행, 같은 열의 값을 서로 더한 결과가 됩니다.
     * 2개의 행렬 arr1과 arr2를 입력받아, 행렬 덧셈의 결과를 반환하는 함수, solution을 완성해주세요.
     */
    @Test
    public void 행렬의덧셈() {
        int[][] answer = {{4,6}, {7,9}};
        int[][] arr1 = {{1,2}, {2,3}};
        int[][] arr2 = {{3,4}, {5,6}};
        int[][] result = new int[arr1.length][arr1[0].length];

        for(int i=0; i<arr1.length; i++) {
            for(int j=0; j<arr1[0].length; j++) {
                result[i][j] = arr1[i][j] + arr2[i][j];
            }
        }

        assertThat(answer, is(result));
    }

    /**
     * 정수 n을 입력받아 n의 약수를 모두 더한 값을 리턴하는 함수, solution을 완성해주세요.
     */
    @Test
    public void 약수의합() {
        final int answer = 28;
        int n = 12;
        int result = 0;

        for(int i=1; i<=n; i++) {
            if(n % i == 0) result += i;
        }

        assertThat(answer, is(result));
    }

    /**
     * 1부터 입력받은 숫자 n 사이에 있는 소수의 개수를 반환하는 함수, solution을 만들어 보세요.
     */
    @Test
    public void 소수찾기() {
        final int answer = 4;
        int n = 10;
        int result = 0;

        for(int i=2; i<=n; i++) {
            boolean isPrime = true;

            //2부터 판별해야하는 숫자(i)까지 나눴을때 소수인지 판별이 가능한데
            //j*j 루트 이용하지 않으면 엄청 큰 수가 들어왔을때 계속 나눠야하다보니 타임아웃이 났다. j<=i 일때는 O(N)
            //모든 수를 나누지 않고 루트를 활용하여 루트의 값만큼만 체크하여 시간복잡도가 줄어든다. j*j<=i 일때는 O(√N)
            //Math.sqrt() 메소드를 이용하면 루트를 구할 수 있다.
            for(int j=2; j*j<=i; j++) {
                if(i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if(isPrime) result++;
        }

        assertThat(answer, is(result));
    }

    /**
     * 문자열 s는 한 개 이상의 단어로 구성되어 있습니다. 각 단어는 하나 이상의 공백문자로 구분되어 있습니다. 각 단어의 짝수번째 알파벳은 대문자로, 홀수번째 알파벳은 소문자로 바꾼 문자열을 리턴하는 함수, solution을 완성하세요.
     * 문자열 전체의 짝/홀수 인덱스가 아니라, 단어(공백을 기준)별로 짝/홀수 인덱스를 판단해야합니다.
     * 첫 번째 글자는 0번째 인덱스로 보아 짝수번째 알파벳으로 처리해야 합니다.
     */
    @Test
    public void 이상한숫자만들기() {
        String answer = "TrY HeLlO WoRlD  ";
        String s = "try hello world  ";

        /*List<String> list = Arrays.stream(s.split(" "))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        int index = 0;

        for(String str : list ) {
            int length = str.length();

            for(int i=0; i<length; i++) {
                if(i % 2 == 0) {
                    sb.append(str.substring(i, i+1).toUpperCase());
                } else {
                    sb.append(str.substring(i, i+1).toLowerCase());
                }
            }

            index++;
            if(index != list.size()) sb.append(" ");
        }

        휴.. 문제의 함정이 있었다. 공백을 기준으로 배열을 만들어서 처리하면 될거라 생각했는데
        공백이 여러개가 있을수 있었다. 심지어 공백은 문자열에 그대로 포함되어야한다. 문제에 설명이 없었는데 이걸 예측하고 만들어야하다니..
        결국 모든 문자를 하나씩 잘라서 공백일때는 공백 그대로 붙이고
        공백을 제외한 문자에만 index를 매겨 홀짝일때 대소문자 처리를 해줬다.
        */

        String[] strArray = s.split("");
        int cnt = 0;
        StringBuilder sb = new StringBuilder();

        for(String str : strArray) {
            if(" ".equals(str)) {
                sb.append(str);
                cnt = 0;
            } else {
                sb.append(cnt % 2 == 0 ? str.toUpperCase() : str.toLowerCase());
                cnt++;
            }
        }

        assertThat(answer, is(sb.toString()));
    }

    /**
     * 대문자와 소문자가 섞여있는 문자열 s가 주어집니다. s에 'p'의 개수와 'y'의 개수를 비교해 같으면 True, 다르면 False를 return 하는 solution를 완성하세요. 'p', 'y' 모두 하나도 없는 경우는 항상 True를 리턴합니다. 단, 개수를 비교할 때 대문자와 소문자는 구별하지 않습니다.
     * 예를 들어 s가 "pPoooyY"면 true를 return하고 "Pyy"라면 false를 return합니다.
     */
    @Test
    public void 문자열내p와y의개수() {
        int pCnt = 0;
        int yCnt = 0;
        boolean answer = true;
        boolean result = false;
        String s = "pPoooyY";

        String[] list = s.toLowerCase().split("");

        for(int i=0; i<list.length; i++) {
            switch (String.valueOf(list[i])) {
                case "p" : pCnt++; break;
                case "y" : yCnt++; break;
                default: break;
            }
        }

        if(pCnt == yCnt) result = true;

        assertThat(answer, is(result));
    }

    /**
     * 수포자는 수학을 포기한 사람의 준말입니다. 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다. 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.
     * 1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
     * 2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
     * 3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
     * 1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때, 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 모의고사() {
        int[] answer = {1};
        int[] answers = {1,2,3,4,5,1,2,3,4,5};
        int[] as1 = {1,2,3,4,5};
        int[] as2 = {2,1,2,3,2,4,2,5};
        int[] as3 = {3,3,1,1,2,2,4,4,5,5};

        int[] asCnt = new int[3];

        for(int i=0; i<answers.length; i++) {
            if(answers[i] == as1[i % as1.length]) asCnt[0]++;
            if(answers[i] == as2[i % as2.length]) asCnt[1]++;
            if(answers[i] == as3[i % as3.length]) asCnt[2]++;
        }

        LinkedHashMap<Integer, Integer> maxMap = new LinkedHashMap<>();
        maxMap.put(1, asCnt[0]); //수포자1
        maxMap.put(2, asCnt[1]); //수포자2
        maxMap.put(3, asCnt[2]); //수포자3

        //수포자 3명의 답안지 중 제일 많이 맞춘 수 추출
        int max = maxMap.get(1);
        if(maxMap.get(2) > max) max = maxMap.get(2);
        if(maxMap.get(3) > max) max = maxMap.get(3);

        ArrayList<Integer> list = new ArrayList<>();

        for(int i=1; i<=maxMap.size(); i++) {
            if(max == maxMap.get(i)) list.add(i);
        }

        int[] result = new int[list.size()];

        //오름차순 정렬을 위해 순서대로 입력
        //list.stream().mapToInt(i->i.intValue()).toArray(); stream을 이용하면 더 깔끔하게 처리가 되지만 실행속도에서 차이가 많이 난다.
        //for->0.08ms, stream->6.0ms
        for(int i=0; i<list.size(); i++) {
            result[i] = list.get(i);
        }

        assertThat(answer, is(result));
    }

    /**
     * 자연수 n을 뒤집어 각 자리 숫자를 원소로 가지는 배열 형태로 리턴해주세요. 예를들어 n이 12345이면 [5,4,3,2,1]을 리턴합니다.
     */
    @Test
    public void 자연수뒤집어배열로만들기() {
        long n = 12345;
        int cnt = 0;
        int[] answer = {5,4,3,2,1};
        int[] result = new int[String.valueOf(n).length()];
        String[] list = String.valueOf(n).split("");

        for(int i=list.length-1; i>=0; i--){
            result[cnt] = Integer.parseInt(list[i]);
            cnt++;
        }

        assertThat(answer, is(result));
    }

    /**
     * 양의 정수 x가 하샤드 수이려면 x의 자릿수의 합으로 x가 나누어져야 합니다. 예를 들어 18의 자릿수 합은 1+8=9이고, 18은 9로 나누어 떨어지므로 18은 하샤드 수입니다.
     * 자연수 x를 입력받아 x가 하샤드 수인지 아닌지 검사하는 함수, solution을 완성해주세요.
     */
    @Test
    public void 하샤드수() {
        boolean answer = true;
        boolean result;
        int x = 10;
        int sum = 0;
        String[] list = String.valueOf(x).split("");

        for(String str : list) {
            sum += Integer.parseInt(str);
        }

        result = (x % sum == 0) ? true : false;

        assertThat(answer, is(result));
    }

    /**
     * String형 배열 seoul의 element중 "Kim"의 위치 x를 찾아, "김서방은 x에 있다"는 String을 반환하는 함수, solution을 완성하세요. seoul에 "Kim"은 오직 한 번만 나타나며 잘못된 값이 입력되는 경우는 없습니다.
     */
    @Test
    public void 서울에서김서방찾기() {
        String answer = "김서방은 1에 있다";
        String[] seoul = {"Jane", "Kim"};
        String result = "";

        //Arrays.asList(array).contains(value)를 사용하면 간략하게 할 수 있지만 굳이 변경하는 비용을 사용해서까지 할 내용은 아닌듯하다.
        for(int i=0; i<seoul.length; i++) {
            if("Kim".equals(seoul[i])) {
                result = "김서방은 " + i + "에 있다";
                break; //오직 한번만 나타나기 때문에 찾으면 break를 써서 불필요한 탐색을 중지한다.
            }
        }

        assertThat(answer, is(result));
    }

    /**
     * 길이가 n이고, "수박수박수박수...."와 같은 패턴을 유지하는 문자열을 리턴하는 함수, solution을 완성하세요. 예를들어 n이 4이면 "수박수박"을 리턴하고 3이라면 "수박수"를 리턴하면 됩니다.
     */
    @Test
    public void 수박수박수() {
        String answer = "수박수박";
        StringBuilder sb = new StringBuilder();
        int n = 4;

        for(int i=0; i<n; i++) {
            sb.append((i % 2 == 0) ? "수" : "박");
        }

        assertThat(answer, is(sb.toString()));
    }

    /**
     * 1937년 Collatz란 사람에 의해 제기된 이 추측은, 주어진 수가 1이 될때까지 다음 작업을 반복하면, 모든 수를 1로 만들 수 있다는 추측입니다. 작업은 다음과 같습니다.
     * 1-1. 입력된 수가 짝수라면 2로 나눕니다.
     * 1-2. 입력된 수가 홀수라면 3을 곱하고 1을 더합니다.
     * 2. 결과로 나온 수에 같은 작업을 1이 될 때까지 반복합니다.
     * 예를 들어, 입력된 수가 6이라면 6→3→10→5→16→8→4→2→1 이 되어 총 8번 만에 1이 됩니다. 위 작업을 몇 번이나 반복해야하는지 반환하는 함수, solution을 완성해 주세요. 단, 작업을 500번을 반복해도 1이 되지 않는다면 –1을 반환해 주세요.
     */
    @Test
    public void 콜라츠추측() {
        int answer = -1;
        int cnt = 0;
        int result = 0;
        long num = 626331;
        long lNum = num;

        while(cnt<=500) {
            if(lNum == 1) break;

            if(lNum % 2 == 0) {
                lNum = lNum / 2L;
            } else {
                lNum = (lNum * 3L) + 1;
            }

            cnt++;
        }

        result = cnt < 500 ? cnt : -1;

        assertThat(answer, is(result));
    }

    /**
     * 어떤 정수들이 있습니다. 이 정수들의 절댓값을 차례대로 담은 정수 배열 absolutes와 이 정수들의 부호를 차례대로 담은 불리언 배열 signs가 매개변수로 주어집니다.
     * 실제 정수들의 합을 구하여 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 음양더하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 9;
        boolean[] signs = {true, false, true};
        int[] absolutes = {4,7,12};
        int sum = 0;

        for(int i=0; i<signs.length; i++) {
            sum += absolutes[i] * (signs[i] ? 1 : -1);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + sum);
        assertThat(answer, is(sum));
    }

    /**
     * 두 수를 입력받아 두 수의 최대공약수와 최소공배수를 반환하는 함수, solution을 완성해 보세요. 배열의 맨 앞에 최대공약수, 그다음 최소공배수를 넣어 반환하면 됩니다.
     * 예를 들어 두 수 3, 12의 최대공약수는 3, 최소공배수는 12이므로 solution(3, 12)는 [3, 12]를 반환해야 합니다.
     */
    @Test
    public void 최대공약수와최대공배수() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {3,12};
        int[] result = new int[2];
        int n = 3;
        int m = 12;

        result[0] = gcd(n, m);
        result[1] = lcm(n, m);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + result);
        assertThat(answer, is(result));
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
     * 두 정수 left와 right가 매개변수로 주어집니다. left부터 right까지의 모든 수들 중에서, 약수의 개수가 짝수인 수는 더하고,
     * 약수의 개수가 홀수인 수는 뺀 수를 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 약수의개수와덧셈() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 52;
        int left = 24;
        int right = 27;
        int sum = 0;

        for(int i=left; i<=right; i++) {
            int cnt = 0;

            for(int j=1; j<=i; j++) {
                if(i % j == 0) cnt++;
            }

            sum += cnt % 2 == 0 ? i * 1 : i * -1;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + sum);
        assertThat(answer, is(sum));
    }

    /**
     * 임의의 양의 정수 n에 대해, n이 어떤 양의 정수 x의 제곱인지 아닌지 판단하려 합니다.
     * n이 양의 정수 x의 제곱이라면 x+1의 제곱을 리턴하고, n이 양의 정수 x의 제곱이 아니라면 -1을 리턴하는 함수를 완성하세요.
     */
    @Test
    public void 정수제곱근판별() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final long answer = 144;
        int n = 121;
        long result = 0;
        double s = Math.sqrt(n); //제곱근의 값을 구한다.

        //구해진 값으로 다시 제곱했을때 값이 일치하면 +1 해준 후 제곱해서 리턴한다.
        result = (Math.pow(s, 2) == n) ? (long) Math.pow(s+1, 2) : -1;

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + result);
        assertThat(answer, is(result));
    }

    /**
     * 배열 arr가 주어집니다. 배열 arr의 각 원소는 숫자 0부터 9까지로 이루어져 있습니다. 이때, 배열 arr에서 연속적으로 나타나는 숫자는 하나만 남기고 전부 제거하려고 합니다.
     * 단, 제거된 후 남은 수들을 반환할 때는 배열 arr의 원소들의 순서를 유지해야 합니다. 예를 들면,
     * arr = [1, 1, 3, 3, 0, 1, 1] 이면 [1, 3, 0, 1] 을 return 합니다.
     * arr = [4, 4, 4, 3, 3] 이면 [4, 3] 을 return 합니다.
     * 배열 arr에서 연속적으로 나타나는 숫자는 제거하고 남은 수들을 return 하는 solution 함수를 완성해 주세요.
     */
    @Test
    public void 같은숫자는싫어() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {1,3,0,1};
        int[] arr = {1,1,3,3,0,1,1};

        List<Integer> tempList = new ArrayList<>();
        tempList.add(arr[0]);

        for(int i=1; i<arr.length; i++) {
            if (arr[i-1] != arr[i]) tempList.add(arr[i]);
        }

        int[] result = new int[tempList.size()];

        for(int j=0; j<result.length; j++) {
            result[j] = tempList.get(j);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "result=" + result);
        assertThat(answer, is(result));
    }

    /**
     * 주어진 숫자 중 3개의 수를 더했을 때 소수가 되는 경우의 개수를 구하려고 합니다. 숫자들이 들어있는 배열 nums가 매개변수로 주어질 때,
     * nums에 있는 숫자들 중 서로 다른 3개를 골라 더했을 때 소수가 되는 경우의 개수를 return 하도록 solution 함수를 완성해주세요.
     *
     * 주의사항 : 같은 수의 조합이 아닐 경우 소수의 값이 동일해도 카운트 해야한다.
     * ex) [2,4,7] = 13, [7,2,4] = 13 일 경우에는 같은 숫자의 다른 조합 소수 이기 때문에 배제한다.
     * ex) [2,4,7] = 13, [2,3,8] = 13 이면 소수는 같지만 조합이 다른 경우이기때문에 이때는 카운트를 해줘야한다.
     * 고로 같은 숫자의 다른 조합은 모두 배제시키고 1개라도 다른 구성이 들어가면 조합을 처리한다.
     */
    public static List<Integer> permutationList = new ArrayList<>();

    @Test
    public void 소수만들기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 4;
        int[] nums = {1,2,7,6,4};
        int result = 0;

        for (int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                for (int k=j+1; k<nums.length; k++) {
                    //소수가 맞을 경우
                    if (findPrime(nums[i] + nums[j] + nums[k])) result++;

                    System.out.println(nums[i] + " " + nums[j] + " " + nums[k]);
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    //소수 찾기
    private boolean findPrime(int num) {
        boolean isPrime = true;

        // 0,1은 소수가 아니므로 제외시킨다.
        if(num <= 1) return isPrime = false;

        for (int j=2; j*j<=num; j++) { //루트를 이용하여 타임아웃을 방지한다.
            if(num % j == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }

    /**
     * 위 소수만들기 소스를 다중 for문이 아닌 재귀호출로 구현해보기
     * 순열, 중복순열, 조합, 중복조합의 종류 중 중복조합을 이용해서 동일한 수의 다른조합을 제외한 항상 다른수의 조합만을 만든다.
     */
    int[] result = new int[3];
    int count;

    @Test
    public void 소수만들기2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 4;
        int[] nums = {1,2,7,6,4};

        //nums 배열에서 숫자 3개를 추출하기 위해 r을 3으로 준다.
        combination(nums, nums.length, 3);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(count));
    }

    private void combination(int[] nums, int n, int r) {
        //r-1만큼 진행해서 0이 되면 3개를 다 추출한것으로 3개의 숫자를 더해서 소수판별을 진행한다.
        if(r == 0) {
            StringBuilder sb = new StringBuilder();
            int add = 0;

            for (int num : result) {
                sb.append(num + " ");
                add += num;
            }

            System.out.println(sb + ": " + add);
            if(findPrime(add)) count++;
            return;
        } else if(n < r) {
            return;
        } else {
            result[r-1] = nums[n-1];
            combination(nums, n-1, r-1); //현재 아이템을 선택한 경우에는 r-1하여 추출을 진행하고 0까지 차례대로 추출한다.
            combination(nums, n-1, r); //현재 아이템을 선택하지 않았을때는 r을 유지하여 다음 아이템을 선택하게 한다.
        }
    }

    /**
     * 어떤 문장의 각 알파벳을 일정한 거리만큼 밀어서 다른 알파벳으로 바꾸는 암호화 방식을 시저 암호라고 합니다. 예를 들어 "AB"는 1만큼 밀면 "BC"가 되고, 3만큼 밀면 "DE"가 됩니다.
     * "z"는 1만큼 밀면 "a"가 됩니다. 문자열 s와 거리 n을 입력받아 s를 n만큼 민 암호문을 만드는 함수, solution을 완성해 보세요.
     */
    @Test
    public void 시저암호() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "e F d";
        String s = "a B z";
        int n = 4;

        //기존에 ASCII 코드를 활용한 코드를 짰으나 넘 지저분하고 보기 힘들었다.
        //로직이 복잡하진 않아 속도는 나쁘지 않았으나 코드가 깔끔하고 좋다는 느낌은 없었다.
        /*String[] sArr = s.split("");
        StringBuilder sb = new StringBuilder();

        for (String ss : sArr) {
            byte[] bytes = ss.getBytes(StandardCharsets.US_ASCII);

            if (bytes[0] >= 65 && bytes[0] <= 90) { //대문자일때
                int upperCase = bytes[0];
                upperCase += n;
                if(upperCase > 90) upperCase = 64 + (upperCase - 90);
                sb.append((char) upperCase);
            } else if (bytes[0] >= 97 && bytes[0] <= 122) { //소문자일때
                int lowerCase = bytes[0];
                lowerCase += n;
                if(lowerCase > 122) lowerCase = 96 + (lowerCase - 122);
                sb.append((char) lowerCase);
            } else if (bytes[0] == 32) {
                sb.append(" ");
            }
        }*/

        //스트림을 활용한 데이터 활용이 있어서 가져와봤다.
        //스트림은 느리다는 편견이 있었는데 기존게 106000ns 스트림이 72500ns로 속도도 더 빨랐다..
        String result = s.chars().map(c -> {
                    int _n = n % 26;

                    if (c >= 'a' && c <= 'z') {
                        return 'a' + (c - 'a' + _n) % 26;
                    } else if (c >= 'A' && c <= 'Z') {
                        return 'A' + (c - 'A' + _n) % 26;
                    } else {
                        return c;
                    }
                })
                .mapToObj(c -> String.valueOf((char)c))
                .reduce((a, b) -> a + b)
                .orElse("");

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 2016년 1월 1일은 금요일입니다. 2016년 a월 b일은 무슨 요일일까요? 두 수 a ,b를 입력받아 2016년 a월 b일이 무슨 요일인지 리턴하는 함수, solution을 완성하세요.
     * 요일의 이름은 일요일부터 토요일까지 각각 SUN,MON,TUE,WED,THU,FRI,SAT 입니다.
     * 예를 들어 a=5, b=24라면 5월 24일은 화요일이므로 문자열 "TUE"를 반환하세요.
     */
    @Test
    public void 이천십육년() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "TUE";
        int a = 5;
        int b = 24;

        //LocalDate의 내장함수를 이용하면 간단하게 출력이 가능하다.
        //getDisplayName에는 날짜의 출력 옵션이 다양해서 출력해야하는 영문 요일의 3글자를 subString을 이용하지 않고도 세팅이 가능했다.
        //프로그래머스의 다른 문제풀이들을 봤을때 직접 월의 일수를 세팅하여 week로 나눠준 값으로 요일을 판단하는 형태의 코드들이 많았다.
        //코테를 하며 느끼는거지만 과연.. 순수 알고리즘을 구현하고 내용을 파악하기 위해 라이브러리 사용을 자제해야하는지 라이브러리를 사용한다면 어디까지 활용해야하는지.. 가끔 딜레마가 온다.
        //라이브러리의 도움없이 순수하게 알고리즘을 구현해보고 그 이후에 리팩토링시에 라이브러리를 도입해서 더 간결한 코드를 짤 수 있는 노력을 해보자.
        String result = LocalDate.of(2016, a, b).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 단어 s의 가운데 글자를 반환하는 함수, solution을 만들어 보세요. 단어의 길이가 짝수라면 가운데 두글자를 반환하면 됩니다.
     */
    @Test
    public void 가운데글자가져오기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "c";
        String s = "abcde";

        //기존의 if처리를 삼항연산자로 바꿨을때 가독성은 어느게 더 편한가
        /*if(s.length() % 2 == 0) {
            result = s.substring(mok-1, mok+1);
        } else {
            result = s.substring(mok, mok+1);
        }

        //length로 짝수 홀수일때를 계산했는데 역시 숫자에 강하면 굳이 구할 필요없이 한방에 substring으로 구현이 가능하다
        int mok = s.length() / 2;
        String result = s.length() % 2 == 0 ? s.substring(mok-1, mok+1) : s.substring(mok, mok+1);
         */

        String result = s.substring((s.length()-1) / 2, s.length()/2 + 1);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
     * 마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
     * 마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
     * completion의 길이는 participant의 길이보다 1 작습니다.
     * 참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
     * 참가자 중에는 동명이인이 있을 수 있습니다.
     */
    @Test
    public void 완주하지_못한_선수(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "mislav";
        String result = "";
        String[] participant = {"mislav", "stanko", "mislav", "ana"};
        String[] completion = {"stanko", "ana", "mislav"};

        HashMap<String, Integer> userMap = new HashMap<>();

        for(String user : participant) {
            //hash map의 getOrDefault를 이용하여 중복체크를 해준다.
            //getOrDefault(key, defaultValue) : map에서 찾는 Key가 없다면 defaultValue를 리턴함
            //해당로직에서 처음들어오는 값은 deafult값 0에다가 1을 더한 0+1 상태로 1로 기록되고 중복이면 1을 가져와서 1+1로 리턴해주기때문에 숫자가 늘어나게 된다.
            //따라서 동일한 이름 즉 동명이인은 count가 증가하게되어 동명이인 만큼의 count 갯수가 value로 반영된다.
            userMap.put(user, userMap.getOrDefault(user, 0) + 1);
        }

        for(String comp : completion) {
            //완주자 리스트를 통해 완주한 사람은 hash map의 key값을 -1 해서 완주한 선수를 체크해준다.
            //참여자 리스트에서 완주자 리스트와 비교해서 -1을 체크 해주면 value값은 0으로 기록된다.
            userMap.put(comp, userMap.get(comp) - 1);
        }

        for (Map.Entry<String, Integer> entry : userMap.entrySet()) {
            //따라서 value가 0보다 크면 동명이인처리가 되어있기 때문에 완주자 리스트에서 동명이인 만큼 동일한 count를 주지 않으면 완주 여부를 구분해준다.
            //hash map의 경우에는 Entry라는 객체를 이용해 key와 value를 지정해주는데
            //userMap.get()을 하게 되면 Set과 관계 없이 다시 hash map으로 가서 해당 key가 가진 value를 전부 탐색하는 경우가 발생하기 때문에 불필요한 search가 발생한다.
            //따라서 Entry 객제 자체를 set으로 가져와서 해당 Entry에 저장된 값만 getValue로 찾으면 불필요한 탐색을 방지할 수 있다.
            if (entry.getValue() > 0) {
                result = entry.getKey();
                break;
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }
}
