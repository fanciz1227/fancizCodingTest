package codingtest.fancizCodingTest.programmers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Pattern;
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

        for (String user : participant) {
            //hash map의 getOrDefault를 이용하여 중복체크를 해준다.
            //getOrDefault(key, defaultValue) : map에서 찾는 Key가 없다면 defaultValue를 리턴함
            //해당로직에서 처음들어오는 값은 deafult값 0에다가 1을 더한 0+1 상태로 1로 기록되고 중복이면 1을 가져와서 1+1로 리턴해주기때문에 숫자가 늘어나게 된다.
            //따라서 동일한 이름 즉 동명이인은 count가 증가하게되어 동명이인 만큼의 count 갯수가 value로 반영된다.
            userMap.put(user, userMap.getOrDefault(user, 0) + 1);
        }

        for (String comp : completion) {
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

    /**
     * array의 각 element 중 divisor로 나누어 떨어지는 값을 오름차순으로 정렬한 배열을 반환하는 함수, solution을 작성해주세요.
     * divisor로 나누어 떨어지는 element가 하나도 없다면 배열에 -1을 담아 반환하세요.
     */
    @Test
    public void 나누어_떨어지는_숫자_배열() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {1, 2, 3, 36};
        int divisor = 1;
        int[] arr = {2, 36, 1, 3};

        List<Integer> list = new ArrayList<>();

        for(int no : arr) {
            if(no % divisor == 0) list.add(no);
        }

        Collections.sort(list);
        int[] result = new int[list.size() == 0 ? 1 : list.size()];

        if(list.size() == 0) {
            result[0] = -1;
        } else {
            for(int i=0; i<list.size(); i++) {
                result[i] = list.get(i);
            }
        }

        /*
        //stream을 통한 처리 위에 긴 코드들이 단 3줄만에 완성이 된다.
        int[] answer2 = Arrays.stream(arr).filter(n -> n % divisor == 0).toArray(); //stream을 통해 filter로 나누어 떨어지는 숫자만 모아서 toArray로 모아준다.
        if(answer2.length == 0) answer2 = new int[] {-1}; //나누어 떨어지는 숫자가 하나도 없을때는 new 연산자로 -1을 넣고 새로 생성한다.
        Arrays.sort(answer2); //생성된 숫자 배열을 sort로 정렬한다.
        */

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 점심시간에 도둑이 들어, 일부 학생이 체육복을 도난당했습니다. 다행히 여벌 체육복이 있는 학생이 이들에게 체육복을 빌려주려 합니다.
     * 학생들의 번호는 체격 순으로 매겨져 있어, 바로 앞번호의 학생이나 바로 뒷번호의 학생에게만 체육복을 빌려줄 수 있습니다.
     * 예를 들어, 4번 학생은 3번 학생이나 5번 학생에게만 체육복을 빌려줄 수 있습니다. 체육복이 없으면 수업을 들을 수 없기 때문에 체육복을 적절히 빌려 최대한 많은 학생이 체육수업을 들어야 합니다.
     * 전체 학생의 수 n, 체육복을 도난당한 학생들의 번호가 담긴 배열 lost, 여벌의 체육복을 가져온 학생들의 번호가 담긴 배열 reserve가 매개변수로 주어질 때, 체육수업을 들을 수 있는 학생의 최댓값을 return 하도록 solution 함수를 작성해주세요.
     */
    @Test
    public void 체육복() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 4;
        int n = 5;
        int[] lost = {2,4};
        int[] reserve = {3};
        int result = 0;

        //정렬이 안된 상태이기 때문에 선정렬을 해준다.
        Arrays.sort(lost);
        Arrays.sort(reserve);
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int re : reserve) {
            map.put(re, map.getOrDefault(re, 0) + 2); //여분 체육복을 가진 사람을 먼저 세팅한다.
        }

        for (int lo : lost) {
            int loNum = Optional.ofNullable(map.get(lo)).orElse(0); //여분 체육복이 아예 없고 잃어버린 상태이면 0으로 세팅하고
            map.put(lo, loNum == 0 ? 0 : loNum - 1); //여분 체육복을 가지고 있던 사람 중 한번 잃어버린 사람은 기존 값 (2개)에서 -1을 해준다. 따라서 가지고 있는 체육복은 1개가 되어 나눠줄 수 없게 된다.
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue()); //기초 데이터 세팅한게 잘 정리되었는지 출력해본다.
        }
        System.out.println();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) { //정리된 hash map에서 차례대로 데이터를 꺼내서 처리해준다.
            if(entry.getValue() > 1) { //가지고 있는 여벌 체육복이 1개 이상일때 즉 2개를 가지고 있어야 나눠 줄 수 있게 한다.
                //여분 체육복은 2개라고 가정하고 앞번호나 뒤번호에 한번 나눠주면 그 이후에는 더 이상 나눠줄 수 없다.
                if(map.get(entry.getKey()-1) != null && map.get(entry.getKey()-1) < 1) { //앞의번호가 있으면서 체육복이 0개이면 처리해준다.
                    map.put(entry.getKey(), map.get(entry.getKey()) - 1); //2개에서 한개를 나눠줘야해서 -1 처리한다.
                    map.put(entry.getKey()-1, map.get(entry.getKey()-1) + 1); //0개에서 한개를 받아 +1 처리한다.
                } else if(map.get(entry.getKey()+1) != null && map.get(entry.getKey()+1) < 1) { //뒤의번호가 있으면서 체육복이 0개이면 처리해준다.
                    map.put(entry.getKey(), map.get(entry.getKey()) - 1); //2개에서 한개를 나눠줘야해서 -1 처리한다.
                    map.put(entry.getKey()+1, map.get(entry.getKey()+1) + 1); //0개에서 한개를 받아 +1 처리한다.
                }
            }
        }

        //체육복을 나눠주는 작업까지 완료된 hash map을 돌면서 체육복이 0개 이상인 하나라도 있는 상태이면 count해준다.
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //전체인원 중 정렬된 hash map 데이터는 체육복 갯수 처리가 된 상태이기 때문에 인원에서 -1을 해서 처리해준다.
            //-1 되지 않고 남은 인원은 체육복이 있다고 간주한다.
            //n이 5개이고 데이터 처리된 hash map이 데이터가 3개이면 남은 2명은 이미 체육복이 있게 된다.
            n -= 1;
            if(entry.getValue() > 0) result++; //체육복이 0개 이상인 하나라도 있는 데이터는 카운트 해준다.
            System.out.println(entry.getKey() + "," + entry.getValue());
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(n + result));
    }

    /**
     * 문자열로 구성된 리스트 strings와, 정수 n이 주어졌을 때, 각 문자열의 인덱스 n번째 글자를 기준으로 오름차순 정렬하려 합니다.
     * 예를 들어 strings가 ["sun", "bed", "car"]이고 n이 1이면 각 단어의 인덱스 1의 문자 "u", "e", "a"로 strings를 정렬합니다.
     * strings는 길이 1 이상, 50이하인 배열입니다.
     * strings의 원소는 소문자 알파벳으로 이루어져 있습니다.
     * strings의 원소는 길이 1 이상, 100이하인 문자열입니다.
     * 모든 strings의 원소의 길이는 n보다 큽니다.
     * 인덱스 1의 문자가 같은 문자열이 여럿 일 경우, 사전순으로 앞선 문자열이 앞쪽에 위치합니다.
     */
    @Test
    public void 문자열_내_마음대로_정렬하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String[] answer = {"abcd", "abce", "cdx"};
        String[] strings = {"abce", "abcd", "cdx"};
        String[] result = new String[strings.length];
        int n = 2;

        ArrayList<String> list = new ArrayList<>();

        //기준이 되는 알파벳을 단어앞에 붙여서 리스트에 추가해준다.
        //이렇게 처리했을 경우엔 알파벳이 아무리 중복값이 생긴다해도 c+문자 로 저장되기 때문에 정렬하는데 아무런 문제가 생기지 않는다.
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i].charAt(n) + strings[i]);
        }

        //알파벳 + 문자 값을 정렬해준다.
        //ex) cabce, cabcd, xcdx -> 이런 경우 결국 cabc는 같지만 사전상으로는 d가 앞서기 때문에 cabcd, cabce, xcdx 순으로 정렬이 완료된다.
        //그러면 알파벳이 앞에 붙어있기때문에 1차 조건인 알파벳 순으로 정렬된 후 string 값을 붙였기 때문에 그 이후 정렬에서 다시 오름차순으로 또 정렬이 되기 떄문에 2차 조건까지 만족할 수 있다.
        //생각을 완전 잘못했다.. hash map으로 정렬을 해보려다 중복값을 허용하지 않아 실패했는데 알파벳과 문자를 따로 정렬해야한다는 압박감에 속아서 제대로 문제 파악을 못했다..
        //확실히 알고리즘 문제는 문제를 정확하게 파악하는게 굉장히 중요한듯 하다
        Collections.sort(list);

        //정렬이 완료된 리스트에서 앞에 붙은 알파벳만 제거 해주면 우리가 원하는 알파벳 정렬 후 문자로 다시 정렬된 값을 얻을 수 있다.
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            result[i] = list.get(i).substring(1);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 새로 생긴 놀이기구는 인기가 매우 많아 줄이 끊이질 않습니다. 이 놀이기구의 원래 이용료는 price원 인데, 놀이기구를 N 번 째 이용한다면 원래 이용료의 N배를 받기로 하였습니다.
     * 즉, 처음 이용료가 100이었다면 2번째에는 200, 3번째에는 300으로 요금이 인상됩니다.
     * 놀이기구를 count번 타게 되면 현재 자신이 가지고 있는 금액에서 얼마가 모자라는지를 return 하도록 solution 함수를 완성하세요.
     * 단, 금액이 부족하지 않으면 0을 return 하세요.
     */
    @Test
    public void 부족한_금액_계산하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final long answer = 10;
        int price = 3;
        int money = 20;
        int count = 4;
        long rPrice = 0;

        for(int i=1; i<=count; i++) {
            rPrice += (price * i);
        }

        long result = money > rPrice ? 0 : (money - rPrice) * -1;

        //등차수열의 합을 이용해 이용료를 계산하고 금액이 부족하지 않은지 판단을 위해 math max를 사용해서 한줄로 풀은 답안이다.
        //수학을 잘해야하나 싶다.. ㅠ
        //return Math.max(price * (count * (count + 1) / 2) - money, 0);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 자연수 n이 매개변수로 주어집니다. n을 3진법 상에서 앞뒤로 뒤집은 후, 이를 다시 10진법으로 표현한 수를 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 삼진법_뒤집기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 229;
        int n = 125;
        int result = 0;
        StringBuilder sb = new StringBuilder();

        //3진법 변환을 위해 3으로 나눠서 몫과 나머지를 구한다.
        //3진법은 3으로 나눴을때 10이상의 크기가 나오지 않아서 3으로 나눈 나머지를 계속 넣어준다.
        //결과는 22111로 뒤집힌 결과가 나온다.
        //소인수분해의 형태로 나누기 때문에 22111이 나오고 우리가 원하는 원래의 3진수는 이 형태에서 reverse를 해서 11122로 만들어줘야 맞다.
        while (n > 0) {
            sb.append(n % 3);
            n /= 3;
        }

        //125를 3진수로 바꾸면 11122가 나온다. 이숫자를 다시 10진수로 바꾸면 아래와 같이 수식을 이용해 바꿀 수 있다.
        //  1  -  1  -  1  -  2  -  2
        // 3(4)  3(3)  3(2)  3(1)  3(0)
        //  81    27    9     6     2
        //제곱근은 증가하면서 각 위치에 맞는 3진수를 곱한 숫자를 모두 더하면 10진수로 변환된다.

        //하지만 우리는 뒤집힌 22111의 값을 구해야한다. 따라서 아래와 같이 진행해야한다.
        //  2  -  2  -  1  -  1  -  1
        // 3(4)  3(3)  3(2)  3(1)  3(0)
        // 162    54    9     3     1

        //여기서 아이러니하게도? for문을 순차적으로 돌리면서 i를 0부터 증가시켜서 4까지 호출하기 위해서는 뒤집힌 상태의 3진수가 아닌 원래의 3진수를 그대로 호출해줘야한다.
        //자세한건 위 표를 보고 참고하면 될것같다.
        char[] arr = sb.reverse().toString().toCharArray();

        //여기서 우리는 11122의 뒤집힌 상태인 22111의 값을 구해야하는데 for문의 특성상 그대로 돌리면 반대의 값이 나오기 때문에
        //22111인 값을 다시 11122로 돌려서 순차대로 for문을 돌려줘야만 뒤집힌 22111 3진수의 10진수 변환 값이 제대로 나오게 된다.
        for(int i=0; i<arr.length; i++) {
            //3진법 -> 10진법을 하기 위해선 3의 제곱근에다 3진법 숫자를 곱한 모든 수를 더하면 10진법이 나온다.
            result += Character.getNumericValue(arr[i]) * (int) Math.pow(3, i);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 대학 교수인 당신은, 상호평가를 통하여 학생들이 제출한 과제물에 학점을 부여하려고 합니다. 아래는 0번부터 4번까지 번호가 매겨진 5명의 학생들이 자신과 다른 학생의 과제를 평가한 점수표입니다.
     * 위의 점수표에서, i행 j열의 값은 i번 학생이 평가한 j번 학생의 과제 점수입니다.
     * 0번 학생이 평가한 점수는 0번 행에담긴 [100, 90, 98, 88, 65]입니다.
     * 0번 학생은 자기 자신에게 100점, 1번 학생에게 90점, 2번 학생에게 98점, 3번 학생에게 88점, 4번 학생에게 65점을 부여했습니다.
     * 2번 학생이 평가한 점수는 2번 행에담긴 [47, 88, 95, 80, 67]입니다.
     * 2번 학생은 0번 학생에게 47점, 1번 학생에게 88점, 자기 자신에게 95점, 3번 학생에게 80점, 4번 학생에게 67점을 부여했습니다.
     * 당신은 각 학생들이 받은 점수의 평균을 구하여, 기준에 따라 학점을 부여하려고 합니다.
     * 만약, 학생들이 자기 자신을 평가한 점수가 유일한 최고점 또는 유일한 최저점이라면 그 점수는 제외하고 평균을 구합니다.
     * 0번 학생이 받은 점수는 0번 열에 담긴 [100, 50, 47, 61, 24]입니다. 자기 자신을 평가한 100점은 자신이 받은 점수 중에서 유일한 최고점이므로, 평균을 구할 때 제외합니다.
     * 0번 학생의 평균 점수는 (50+47+61+24) / 4 = 45.5입니다.
     * 4번 학생이 받은 점수는 4번 열에 담긴 [65, 77, 67, 65, 65]입니다. 자기 자신을 평가한 65점은 자신이 받은 점수 중에서 최저점이지만 같은 점수가 2개 더 있으므로, 유일한 최저점이 아닙니다. 따라서, 평균을 구할 때 제외하지 않습니다.
     * 4번 학생의 평균 점수는 (65+77+67+65+65) / 5 = 67.8입니다.
     * 제외할 점수는 제외하고 평균을 구한 후, 아래 기준에 따라 학점을 부여합니다.
     */
    @Test
    public void 상호평가() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "FBABD";
        int[][] scores = {{100,90,98,88,65}, {50,45,99,85,77}, {47,88,95,80,67}, {61,57,100,80,65}, {24,90,94,75,65}};
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<scores[0].length; i++) {
            int sum = 0;
            int maxCnt = 0;
            int minCnt = 0;
            int selfScore = scores[i][i];
            int avgRow = scores[0].length;

            for (int j=0; j<scores[0].length; j++) {
                if(selfScore >= scores[j][i]) maxCnt += 1; //본인의 점수를 돌렸을때 같거나 큰 점수를 비교하여 count 해준다. 유일하다면 유일한 최고점이기때문에 maxCnt가 5가 나온다.
                if(selfScore <= scores[j][i]) minCnt += 1; //위와 같은 로직으로 최저점을 count 해준다. 유일하다면 minCnt가 5가 나온다.

                sum += scores[j][i];
            }

            //for문을 통해 본인의 점수가 유일한 최고점, 유일한 최저점일때만 합계에서 제외를 해야한다.
            //여기서 포인트는 '유일한'이다 본인의 점수가 최고점이나 최저점인데 동일한 점수를 다른사람에게서 받았다면 유일하지 않기 때문에 합계에서 제외되지 않는다.
            //위 maxCnt와 minCnt를 구했을때 유일한 최고점은 maxCnt=5, minCnt=1 일때이며, 유일한 최저점은 maxCnt=1, minCnt=5 일때이다.
            //그 외에 케이스는 최고점이나 최저점이 동시에 여러개 존재하는 경우로서 유일하지 않기 때문에 합계에 포함하여 계산을 해주면 된다.
            if((maxCnt == scores[0].length && minCnt == 1) || (maxCnt == 1 && minCnt == scores[0].length)) {
                sum -= selfScore;
                avgRow = scores[0].length-1;
            }

            double avg = (double) sum / avgRow;

            if (avg >= 90) {
                sb.append("A");
            } else if (avg >= 80 && avg < 90) {
                sb.append("B");
            } else if (avg >= 70 && avg < 80) {
                sb.append("C");
            } else if (avg >= 50 && avg < 70) {
                sb.append("D");
            } else if (avg < 50) {
                sb.append("F");
            }

            //결과 출력 예시
            System.out.println("no" + i + ":" + sum + ", maxCnt:" + maxCnt + ", minCnt:" + minCnt + ", avg:" + avg);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(sb.toString()));
    }

    /**
     * 길이가 같은 두 1차원 정수 배열 a, b가 매개변수로 주어집니다. a와 b의 내적을 return 하도록 solution 함수를 완성해주세요.
     * 이때, a와 b의 내적은 a[0]*b[0] + a[1]*b[1] + ... + a[n-1]*b[n-1] 입니다. (n은 a, b의 길이)
     * a와 b의 내적은 1*(-3) + 2*(-1) + 3*0 + 4*2 = 3 입니다.
     */
    @Test
    public void 내적() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = -2;
        int[] a = {-1,0,1};
        int[] b = {1,0,-1};
        int result = 0;

        for (int i=0; i<a.length; i++) {
            result += a[i] * b[i];
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * S사에서는 각 부서에 필요한 물품을 지원해 주기 위해 부서별로 물품을 구매하는데 필요한 금액을 조사했습니다. 그러나, 전체 예산이 정해져 있기 때문에 모든 부서의 물품을 구매해 줄 수는 없습니다. 그래서 최대한 많은 부서의 물품을 구매해 줄 수 있도록 하려고 합니다.
     * 물품을 구매해 줄 때는 각 부서가 신청한 금액만큼을 모두 지원해 줘야 합니다. 예를 들어 1,000원을 신청한 부서에는 정확히 1,000원을 지원해야 하며, 1,000원보다 적은 금액을 지원해 줄 수는 없습니다.
     * 부서별로 신청한 금액이 들어있는 배열 d와 예산 budget이 매개변수로 주어질 때, 최대 몇 개의 부서에 물품을 지원할 수 있는지 return 하도록 solution 함수를 완성해주세요.
     */

    @Test
    public void 예산() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 3;
        int[] d = {1,3,2,5,4};
        int result = 0;
        int budget = 9;

        //sort가 핵심이다. 문제의 함정이 있는 단순히 문제를 처음 봤을때는 순열을 이용해서 해결해야하나 했는데 자세히 살펴보면 꼭 예산을 전체를 다 써야하지 않는다.
        //예산에 제일 가까운 부서의 합계를 구해내면 되기 떄문에 가장 많이 부서의 합을 구해낼 수 있고 예산의 근사치 값을 얻어내려면
        //오름차순 sort를 한 다음에 하나씩 더해가면 가장 많은 숫자의 부서를 구해낼 수 있는것이다.
        Arrays.sort(d);

        for (int no : d) {
            if(no > budget) break;

            budget -= no;
            result++;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 개발자가 사용하는 언어와 언어 선호도를 입력하면 그에 맞는 직업군을 추천해주는 알고리즘을 개발하려고 합니다.
     * 아래 표는 5개 직업군 별로 많이 사용하는 5개 언어에 직업군 언어 점수를 부여한 표입니다.
     * 점수	SI	CONTENTS	HARDWARE	PORTAL	GAME
     * 5	JAVA	JAVASCRIPT	C	JAVA	C++
     * 4	JAVASCRIPT	JAVA	C++	JAVASCRIPT	C#
     * 3	SQL	PYTHON	PYTHON	PYTHON	JAVASCRIPT
     * 2	PYTHON	SQL	JAVA	KOTLIN	C
     * 1	C#	C++	JAVASCRIPT	PHP	JAVA
     * 예를 들면, SQL의 SI 직업군 언어 점수는 3점이지만 CONTENTS 직업군 언어 점수는 2점입니다. SQL의 HARDWARE, PORTAL, GAME 직업군 언어 점수는 0점입니다.
     * 직업군 언어 점수를 정리한 문자열 배열 table, 개발자가 사용하는 언어를 담은 문자열 배열 languages, 언어 선호도를 담은 정수 배열 preference가 매개변수로 주어집니다.
     * 개발자가 사용하는 언어의 언어 선호도 x 직업군 언어 점수의 총합이 가장 높은 직업군을 return 하도록 solution 함수를 완성해주세요.
     * 총합이 같은 직업군이 여러 개일 경우, 이름이 사전 순으로 가장 빠른 직업군을 return 해주세요.
     */
    @Test
    public void 직업군_추천하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String answer = "PORTAL";
        String result = "";
        String[] table = {"SI JAVA JAVASCRIPT SQL PYTHON C#",
                "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++",
                "HARDWARE C C++ PYTHON JAVA JAVASCRIPT",
                "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP",
                "GAME C++ C# JAVASCRIPT C JAVA"};
        String[] languages = {"JAVA", "JAVASCRIPT"};
        int[] preference = {7,5};

        HashMap<String, String[]> rankMap = new HashMap<>();
        HashMap<String, Integer> langMap = new HashMap<>();
        HashMap<String, Integer> resultMap = new HashMap<>();

        //직업군 선호도 데이터 HashMap 데이터로 변경
        //key : SI , value : JAVA JAVASCRIPT SQL PYTHON C#
        for (String str : table) {
            rankMap.put(str.substring(0, str.indexOf(" ")), str.substring(str.indexOf(" ")+1).split(" "));
        }

        //개발자 언어 선호도 HashMap
        for (int i=0; i<languages.length; i++) {
            langMap.put(languages[i], preference[i]);
        }

        //선호도 별로 정리된 데이터에서 입력된 언어의 선호도 점수를 매핑 시켜 resultMap으로 put
        for (Map.Entry<String, String[]> rankEntry : rankMap.entrySet()) {
            int sum = 0;

            for (Map.Entry<String, Integer> langEntry : langMap.entrySet()) {
                int grade = 5;
                int index = Arrays.asList(rankEntry.getValue()).indexOf(langEntry.getKey());
                grade = index > -1 ? grade - index : 0;
                grade *= langEntry.getValue();
                sum += grade;
            }

            resultMap.put(rankEntry.getKey(), sum);
        }

        //같은 점수가 있을 경우를 대비하여 언어를 내림차순(사전순)으로 선정렬
        List<Map.Entry<String, Integer>> resultList = new ArrayList<>(resultMap.entrySet());
        resultList.sort(Map.Entry.comparingByKey());
        int max = 0;

        for (Map.Entry<String, Integer> resultEntry : resultList) {
            if (max < resultEntry.getValue()) {
                max = resultEntry.getValue();
                result = resultEntry.getKey();
            }
        }

        /* 나는 HashMap으로 풀었는데 그냥 주어진 String을 배열로 풀어놔서 그 안에 있는 languages 배열만큼 돌려서 합을 구했다..
        세부 로직은 indexOf를 이용해서 풀은건 비슷하게 접근했는데 굳이 HashMap을 접근안하고도 풀수있었다..

        for(String str : table){
            String[] t = str.split(" ");
            String tname = t[0];
            int tscore = 0;

            for(int i=0;i<languages.length;i++){
                int idx = Arrays.asList(t).indexOf(languages[i]);
                if(idx >- 1) tscore += preference[i] * (6 - idx);
            }

            if(score == tscore && answer.compareTo(tname) > 0) answer = tname;
            if(score<tscore){
                score = tscore;
                answer = tname;
            }
        }
         */

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 네오는 평소 프로도가 비상금을 숨겨놓는 장소를 알려줄 비밀지도를 손에 넣었다. 그런데 이 비밀지도는 숫자로 암호화되어 있어 위치를 확인하기 위해서는 암호를 해독해야 한다. 다행히 지도 암호를 해독할 방법을 적어놓은 메모도 함께 발견했다.
     * 지도는 한 변의 길이가 n인 정사각형 배열 형태로, 각 칸은 "공백"(" ") 또는 "벽"("#") 두 종류로 이루어져 있다.
     * 전체 지도는 두 장의 지도를 겹쳐서 얻을 수 있다. 각각 "지도 1"과 "지도 2"라고 하자. 지도 1 또는 지도 2 중 어느 하나라도 벽인 부분은 전체 지도에서도 벽이다. 지도 1과 지도 2에서 모두 공백인 부분은 전체 지도에서도 공백이다.
     * "지도 1"과 "지도 2"는 각각 정수 배열로 암호화되어 있다.
     * 암호화된 배열은 지도의 각 가로줄에서 벽 부분을 1, 공백 부분을 0으로 부호화했을 때 얻어지는 이진수에 해당하는 값의 배열이다.
     */
    @Test
    public void 비밀지도() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final String[] answer = {"######", "###  #", "##  ##", " #### ", " #####", "### # "};
        int[] arr1 = {46, 33, 33 ,22, 31, 50};
        int[] arr2 = {27 ,56, 19, 14, 14, 10};
        int n = 6;
        String[] result = new String[n];

        //정사각형 변의 길이 n만큼 반복한다.
        for (int i=0; i<n; i++) {
            //arr1,arr2 배열의 요소를 변의 길이만큼 2진수로 변환
            char[] binaryArr1 = decimalToBinary(n, arr1[i]);
            char[] binaryArr2 = decimalToBinary(n, arr2[i]);

            StringBuilder sb = new StringBuilder();

            //변의 길이만큼 10진수에서 2진수로 변환했기 때문에 동일하게 n만큼 반복한다.
            for (int j=0; j<n; j++) {
                //지도 1 또는 지도 2 중 어느 하나라도 벽인 부분은 전체 지도에서도 벽이다.
                if(binaryArr1[j] == '1' || binaryArr2[j] == '1') {
                    sb.append("#");
                } else {
                    sb.append(" ");
                }
            }

            result[i] = sb.toString();
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    //10진수 -> 2진수 변환
    private char[] decimalToBinary(int n, int decimal) {
        StringBuilder binaryStr = new StringBuilder();

        //지도의 변이 5나 6이냐에 따라서 2진수 표현도 동일하게 변의 길이에 맞춰줘야하기 때문에 while이 아닌 for문으로 변의 길이 만큼 돌려준다.
        //ex) n=5일때 10을 변환하면 01010이다. n=6일때 10을 변환하면 001010이다.
        //따라서 n의 길이 만큼 2진수의 길이도 맞춰줘야한다.
        //끙.. Integer.toBinaryString(33 | 56) | 연산자를 이용해서 2개의 값을 넘겨주면 우리가 구해야하는 값이 그대로 출력된다..
        //다만 Integer.toBinaryString을 이용한 2진수 변환도 결국 변의 길이만큼 자리수를 맞춰줘야해서 소인수분해로 진행했다.
        for (int i=0; i<n; i++) {
            binaryStr.append(decimal % 2);
            decimal /= 2;
        }

        //소인수분해로 했기 때문에 결과가 뒤집힌 상태로 나오기 때문에 다시 한번 뒤집어서 정상적으로 표현해준다.
        return binaryStr.reverse().toString().toCharArray();
    }

    /**
     * 카카오톡 게임별의 하반기 신규 서비스로 다트 게임을 출시하기로 했다. 다트 게임은 다트판에 다트를 세 차례 던져 그 점수의 합계로 실력을 겨루는 게임으로, 모두가 간단히 즐길 수 있다.
     * 갓 입사한 무지는 코딩 실력을 인정받아 게임의 핵심 부분인 점수 계산 로직을 맡게 되었다. 다트 게임의 점수 계산 로직은 아래와 같다.
     * 다트 게임은 총 3번의 기회로 구성된다.
     * 각 기회마다 얻을 수 있는 점수는 0점에서 10점까지이다.
     * 점수와 함께 Single(S), Double(D), Triple(T) 영역이 존재하고 각 영역 당첨 시 점수에서 1제곱, 2제곱, 3제곱 (점수1 , 점수2 , 점수3 )으로 계산된다.
     * 옵션으로 스타상(*) , 아차상(#)이 존재하며 스타상(*) 당첨 시 해당 점수와 바로 전에 얻은 점수를 각 2배로 만든다. 아차상(#) 당첨 시 해당 점수는 마이너스된다.
     * 스타상(*)은 첫 번째 기회에서도 나올 수 있다. 이 경우 첫 번째 스타상(*)의 점수만 2배가 된다. (예제 4번 참고)
     * 스타상(*)의 효과는 다른 스타상(*)의 효과와 중첩될 수 있다. 이 경우 중첩된 스타상(*) 점수는 4배가 된다. (예제 4번 참고)
     * 스타상(*)의 효과는 아차상(#)의 효과와 중첩될 수 있다. 이 경우 중첩된 아차상(#)의 점수는 -2배가 된다. (예제 5번 참고)
     * Single(S), Double(D), Triple(T)은 점수마다 하나씩 존재한다.
     * 스타상(*), 아차상(#)은 점수마다 둘 중 하나만 존재할 수 있으며, 존재하지 않을 수도 있다.
     * 0~10의 정수와 문자 S, D, T, *, #로 구성된 문자열이 입력될 시 총점수를 반환하는 함수를 작성하라.
     */
    @Test
    public void 다트게임() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 9;
        String dartResult = "1D2S#10S";

        String[] dartArr = dartResult.split("");
        String[] prizeArr = new String[3];
        int[] scoreArr = new int[3];

        String score = "";
        int result = 0;
        int arrCnt = 0;

        //들어온 문자열을 하나씩 분리해서 점수와 상을 분리 한다.
        for (int i=0; i<dartArr.length; i++) {
            if (Pattern.matches("^[0-9]*$", dartArr[i])) { //정규식을 이용해 숫자인지 체크한다.
                //10이 들어오는 경우에는 두번 숫자가 붙어야하기 때문에 String으로 이어 붙여준 후에 사용하도록 한다.
                score += dartArr[i];
            } else if (Pattern.matches("^[A-Z]*$", dartArr[i])) { //정규식을 이용해 영문 대문자인지 체크한다.
                int pow = 0;

                switch (dartArr[i]) {
                    case "S" :
                        pow = 1;
                        break;
                    case "D" :
                        pow = 2;
                        break;
                    case "T" :
                        pow = 3;
                        break;
                    default:
                        pow = 0;
                        break;
                }

                //SDT에 맞는 각 제곱근을 구해서 얻은 점수에 제곱하여 점수 배열을 만들어준다.
                scoreArr[arrCnt] = (int) Math.pow(Integer.parseInt(score), pow);
                score = ""; //SDT가 들어오면 점수 구성이 끝났기 때문에 점수 string을 초기화 하고 arrCnt를 증가시킨다.
                arrCnt++;
            } else {
                //arrCnt를 활용하여 몇번째에 붙은 스타상, 아차상인지 구분해준다. 이미 증가한 상태의 cnt여서 -1을 해줘야 정확한 위치에 반영된다.
                prizeArr[arrCnt-1] = dartArr[i];
            }
        }

        //for문을 돌때 scr에 미리 값을 반영해놓기 위해서 역순으로 돌렸다.
        for (int i=scoreArr.length-1; i>=0; i--) {
            int scr = scoreArr[i];

            if (prizeArr[i] != null && "*".equals(prizeArr[i])) {
                //스타상의 경우 현재 점수와 바로 전에 얻은 점수를 각 2배로 만들어야 하기 때문에
                //전의 점수가 없는 scoreArr[0]을 제외하고 이전 값을 2배로 만들어서 배열에 반영한다.
                if (i >= 1) scoreArr[i-1] *= 2;
                scr *= 2; //스타상은 x2를 한다.
            } else if (prizeArr[i] != null && "#".equals(prizeArr[i])){
                scr *= -1; //아차상은 x(-1)을 한다.
            }

            result += scr;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 당신은 폰켓몬을 잡기 위한 오랜 여행 끝에, 홍 박사님의 연구실에 도착했습니다. 홍 박사님은 당신에게 자신의 연구실에 있는 총 N 마리의 폰켓몬 중에서 N/2마리를 가져가도 좋다고 했습니다.
     * 홍 박사님 연구실의 폰켓몬은 종류에 따라 번호를 붙여 구분합니다. 따라서 같은 종류의 폰켓몬은 같은 번호를 가지고 있습니다. 예를 들어 연구실에 총 4마리의 폰켓몬이 있고, 각 폰켓몬의 종류 번호가 [3번, 1번, 2번, 3번]이라면 이는 3번 폰켓몬 두 마리,
     * 1번 폰켓몬 한 마리, 2번 폰켓몬 한 마리가 있음을 나타냅니다. 이때, 4마리의 폰켓몬 중 2마리를 고르는 방법은 다음과 같이 6가지가 있습니다.
     *
     * 첫 번째(3번), 두 번째(1번) 폰켓몬을 선택
     * 첫 번째(3번), 세 번째(2번) 폰켓몬을 선택
     * 첫 번째(3번), 네 번째(3번) 폰켓몬을 선택
     * 두 번째(1번), 세 번째(2번) 폰켓몬을 선택
     * 두 번째(1번), 네 번째(3번) 폰켓몬을 선택
     * 세 번째(2번), 네 번째(3번) 폰켓몬을 선택
     * 이때, 첫 번째(3번) 폰켓몬과 네 번째(3번) 폰켓몬을 선택하는 방법은 한 종류(3번 폰켓몬 두 마리)의 폰켓몬만 가질 수 있지만, 다른 방법들은 모두 두 종류의 폰켓몬을 가질 수 있습니다. 따라서 위 예시에서 가질 수 있는 폰켓몬 종류 수의 최댓값은 2가 됩니다.
     * 당신은 최대한 다양한 종류의 폰켓몬을 가지길 원하기 때문에, 최대한 많은 종류의 폰켓몬을 포함해서 N/2마리를 선택하려 합니다. N마리 폰켓몬의 종류 번호가 담긴 배열 nums가 매개변수로 주어질 때,
     * N/2마리의 폰켓몬을 선택하는 방법 중, 가장 많은 종류의 폰켓몬을 선택하는 방법을 찾아, 그때의 폰켓몬 종류 번호의 개수를 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 폰켓몬() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 3;
        int[] nums = {3, 3, 3, 2, 2, 4};
        int result = 1; //맨 처음 값은 카운트가 되지 않기 때문에 1부터 시작한다.

        Arrays.sort(nums); //정렬을 해서 비교할때 오류가 나지 않게 만든다.

        //sort할때 기본적으로 지연이 되며 모든 값을 다 비교해야해서 값이 좀 큰경우에는 시간이 오래걸리는 편이다..
        for (int i=0; i<nums.length-1; i++) {
            if(result == nums.length / 2) break; //배열/2 만큼만 선택할 수 있기 때문에 result count가 해당 숫자만큼 도달하면 바로 종료한다.

            if(nums[i] != nums[i+1]) result++; //정렬된 array에서 값을 비교하면서 다르면 result를 증가시켜준다. 어차피 배열/2만큼이 최대치이기 때문에 다른 숫자가 발견되는 경우에 count를 증가시키는 것으로 체크해줬다.
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 슈퍼 게임 개발자 오렐리는 큰 고민에 빠졌다. 그녀가 만든 프랜즈 오천성이 대성공을 거뒀지만, 요즘 신규 사용자의 수가 급감한 것이다. 원인은 신규 사용자와 기존 사용자 사이에 스테이지 차이가 너무 큰 것이 문제였다
     * 이 문제를 어떻게 할까 고민 한 그녀는 동적으로 게임 시간을 늘려서 난이도를 조절하기로 했다. 역시 슈퍼 개발자라 대부분의 로직은 쉽게 구현했지만, 실패율을 구하는 부분에서 위기에 빠지고 말았다. 오렐리를 위해 실패율을 구하는 코드를 완성하라.
     * 실패율은 다음과 같이 정의한다. 스테이지에 도달했으나 아직 클리어하지 못한 플레이어의 수 / 스테이지에 도달한 플레이어 수
     * 전체 스테이지의 개수 N, 게임을 이용하는 사용자가 현재 멈춰있는 스테이지의 번호가 담긴 배열 stages가 매개변수로 주어질 때, 실패율이 높은 스테이지부터 내림차순으로 스테이지의 번호가 담겨있는 배열을 return 하도록 solution 함수를 완성하라.
     *
     * 제한사항
     * 스테이지의 개수 N은 1 이상 500 이하의 자연수이다.
     * stages의 길이는 1 이상 200,000 이하이다.
     * stages에는 1 이상 N + 1 이하의 자연수가 담겨있다.
     * 각 자연수는 사용자가 현재 도전 중인 스테이지의 번호를 나타낸다.
     * 단, N + 1 은 마지막 스테이지(N 번째 스테이지) 까지 클리어 한 사용자를 나타낸다.
     * 만약 실패율이 같은 스테이지가 있다면 작은 번호의 스테이지가 먼저 오도록 하면 된다.
     * 스테이지에 도달한 유저가 없는 경우 해당 스테이지의 실패율은 0 으로 정의한다.
     */
    @Test
    public void 실패율() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {3, 4, 2, 1, 5};

        HashMap<Integer, Double> clearMap = new HashMap<>();
        int n = 5;
        int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};
        int[] result = new int[n];

        //스테이지 1부터 5까지 반복한다.
        for (int i=1; i<=n; i++) {
            int clearCnt = 0;
            int notClearCnt = 0;

            for (int j=0; j<stages.length; j++) {
                if(stages[j] >= i) clearCnt++; //스테이지배열에서 현재 스테이지값과 비교해 배열값이 크면 해당 스테이지에 도달한것으로 체크한다.

                if(stages[j] == i) notClearCnt++; //스테이지배열에서 현재 스테이지값과 비교해 배열값이 같으면 클리어하지 못한것으로 체크한다.
            }

            //스테이지를 1부터 5까지 반복할때 해당 스테이지에 도달한 유저가 없을 경우 0으로 처리해준다.
            //나머지는 '클리어하지 못한 유저 수 / 스테이지에 도달한 유저 수' 로 값을 구해준다.
            clearMap.put(i, clearCnt == 0 ? 0 : (double) notClearCnt / clearCnt);
        }

        List<Map.Entry<Integer, Double>> resultList = new ArrayList<>(clearMap.entrySet());
        resultList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); //HashMap의 value를 기준으로 오름차순으로 정렬한다.

        int cnt = 0;

        for (Map.Entry<Integer, Double> entry : resultList) {
            result[cnt] = entry.getKey();
            cnt++;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    /**
     * 복서 선수들의 몸무게 weights와, 복서 선수들의 전적을 나타내는 head2head가 매개변수로 주어집니다. 복서 선수들의 번호를 다음과 같은 순서로 정렬한 후 return 하도록 solution 함수를 완성해주세요.
     *
     * 전체 승률이 높은 복서의 번호가 앞쪽으로 갑니다. 아직 다른 복서랑 붙어본 적이 없는 복서의 승률은 0%로 취급합니다.
     * 승률이 동일한 복서의 번호들 중에서는 자신보다 몸무게가 무거운 복서를 이긴 횟수가 많은 복서의 번호가 앞쪽으로 갑니다.
     * 자신보다 무거운 복서를 이긴 횟수까지 동일한 복서의 번호들 중에서는 자기 몸무게가 무거운 복서의 번호가 앞쪽으로 갑니다.
     * 자기 몸무게까지 동일한 복서의 번호들 중에서는 작은 번호가 앞쪽으로 갑니다.
     */
    @Test
    public void 복서_정렬하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int[] answer = {3,4,1,2};

        int[] weights = {50, 82, 75, 120};
        int[] result = new int[weights.length];
        String[] head2head = {"NLWL", "WNLL", "LWNW", "WWLN"};
        ArrayList<Boxer> boxerList = new ArrayList<>();

        for (int i=0; i<weights.length; i++) { //선수의 수 만큼 반복한다.
            String[] headArr = head2head[i].split(""); //해당 선수에 대한 전적을 가져온다.
            int winCnt = 0;
            int loseCnt = 0;
            int nCnt = 0;
            int heavyBox = 0;

            for (int j=0; j<headArr.length; j++) {
                switch (headArr[j]) {
                    case "W" :
                        if (weights[i] < weights[j]) heavyBox++; //본인보다 무거운 상대를 이겼을때 heavyBox를 증가시킨다.
                        winCnt++; //이겼을때 증가 한다.
                        break;
                    case "L" :
                        loseCnt++; //졌을때 증가 한다.
                        break;
                    default:
                        nCnt++; //자기 자신의 값이거나 전적자체가 아예 없는 경우에 증가한다.
                        break;
                }
            }

            //Boxer 객체에 선수번호, 승률, 무거운상대이긴횟수, 본인 무게 를 계산하여 생성한다.
            Boxer boxer = new Boxer(i+1, nCnt == weights.length ? 0 : (double) winCnt/(winCnt+loseCnt), heavyBox, weights[i]);
            boxerList.add(boxer); //생성된 객체를 list에 넣어준다.
        }

        //list에 들어온 Boxer 객체들중에서
        //승률 > 무거운상대이긴횟수 > 본인 무게 를 선언된 순서대로 내림차순으로 정렬하고
        //동승률이거나 무거운상대이긴횟수가 같거나 무게마저 같게되면 번호기준을 오름차순으로 재정렬해서 보여준다.
        //Comparator안에 지원하는 thenComparing를 이용하여 다중 조건 정렬을 구현했다.
        //다른 사람들 답은 직접 Comparator를 구현했다.. 후에 다시 이 부분을 라이브러리를 이용하지 않고
        //객체도 활용했는데 이걸 직접 구현해보거나 배열로 풀어봐야겠다..
        boxerList.sort(Comparator.comparing(Boxer::getWinRate)
                .thenComparing(Boxer::getHeavyCnt)
                .thenComparing(Boxer::getWeight)
                .thenComparing(Boxer::getNo).reversed());

        //생성된 리스트에서 정렬된 순서대로 result 배열에 번호를 대입해준다.
        for (int r=0; r<boxerList.size(); r++) {
            result[r] = boxerList.get(r).getNo();
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }

    private class Boxer {
        private int no;
        private double winRate;
        private int heavyCnt;
        private int weight;

        public int getNo() {
            return no;
        }

        public double getWinRate() {
            return winRate;
        }

        public int getHeavyCnt() {
            return heavyCnt;
        }

        public int getWeight() {
            return weight;
        }

        Boxer(int no, double winRate, int heavyCnt, int weight) {
            this.no = no;
            this.winRate = winRate;
            this.heavyCnt = heavyCnt;
            this.weight = weight;
        }
    }

    /**
     * 가정합니다. (그림에서는 화면표시 제약으로 5칸만으로 표현하였음)
     * 게임 화면의 격자의 상태가 담긴 2차원 배열 board와 인형을 집기 위해 크레인을 작동시킨 위치가 담긴 배열 moves가 매개변수로 주어질 때,
     * 크레인을 모두 작동시킨 후 터트려져 사라진 인형의 개수를 return 하도록 solution 함수를 완성해주세요.
     */
    @Test
    public void 크레인_인형뽑기_게임() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final int answer = 4;
        int[] moves = {1, 2, 3, 3, 2, 3, 1};
        int[][] board = {{0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 2, 1, 0, 0}, {0, 2, 1, 0, 0}, {0, 2, 1, 0, 0}};
        int result = 0;
        int compareNo = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(0); //맨처음 비교대상을 위해 0으로 초기화 한 상태에서 시작한다.

        //인형을 뽑는 횟수만큼 반복한다.
        for (int move : moves) {
            for (int i=0; i<board[move - 1].length; i++) { //board[0]의 배열 수 만큼 반복한다.
                if (board[i][move - 1] != 0) { //board의 move-1 번째 인덱스에 있는 값을 반복해서 추출하다 0이 아닌 숫자, 즉 인형이 발견되면 뽑는다.
                    stack.push(board[i][move - 1]); //스택에 추출된 인형 번호를 넣는다.
                    board[i][move - 1] = 0; //추출된 인덱스는 이제 인형이 없기 때문에 0으로 초기화 해준다.

                    if(compareNo == stack.peek()) { //현재 스택의 가장 마지막 값과 비교번호를 대조해서 같으면 이전 인덱스의 스택값과 현재 인덱스의 스택값이 같으므로 2개를 스택에서 제거한다.
                        stack.pop(); //위에서 이미 push 된 상태이기 때문에 pop을 2번 호출하여 동일한 인형의 숫자를 제거 해준다.
                        stack.pop();
                        result += 2; //없어진 인형은 2개이므로 2를 결과값에 더해준다.
                    }

                    compareNo = stack.peek(); //인형이 발견된 순간 위의 조건들을 거쳐 가장 마지막에 남아있는 스택의 마지막 값을 가져와서 비교번호에 초기화한다.
                    break;
                }
            }
        }

        //처음엔 for문으로 별도의 배열을 만들었는데 하다보니 굳이 배열을 따로 뺄 필요없이 for문 안에서 바로바로 처리가 가능했다.
        //확실히 코딩테스트 문제들을 많이 접하다보니 어느정도 알고리즘 구현하는게 눈에 익숙해지기 시작했다.

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(result));
    }
}
