package programmers.fancizProgrammersQuiz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
}
