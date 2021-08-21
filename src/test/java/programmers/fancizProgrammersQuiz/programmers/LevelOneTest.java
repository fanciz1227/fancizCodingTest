package programmers.fancizProgrammersQuiz.programmers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
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
        int answer = 28;
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
            boolean isDecimal = true;

            //2부터 판별해야하는 숫자(i)까지 나눴을때 소수인지 판별이 가능한데
            //j*j 루트 이용하지 않으면 엄청 큰 수가 들어왔을때 계속 나눠야하다보니 타임아웃이 났다. j<=i 일때는 O(N)
            //모든 수를 나누지 않고 루트를 활용하여 루트의 값만큼만 체크하여 시간복잡도가 줄어든다. j*j<=i 일때는 O(√N)
            //Math.sqrt() 메소드를 이용하면 루트를 구할 수 있다.
            for(int j=2; j*j<=i; j++) {
                if(i % j == 0) {
                    isDecimal = false;
                    break;
                }
            }

            if(isDecimal) result++;
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
        boolean answer = true;
        boolean result = false;
        String s = "pPoooyY";
        int pCnt = 0;
        int yCnt = 0;

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
        int[] answer = {5,4,3,2,1};
        long n = 12345;
        int[] result = new int[String.valueOf(n).length()];
        String[] list = String.valueOf(n).split("");
        int cnt = 0;

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
        int x = 10;
        String[] list = String.valueOf(x).split("");
        int sum = 0;
        boolean result;

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
        long num = 626331;
        int cnt = 0;
        long lNum = num;
        int result = 0;

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
}
