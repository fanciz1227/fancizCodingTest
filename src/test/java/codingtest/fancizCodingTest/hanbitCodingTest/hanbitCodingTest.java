package codingtest.fancizCodingTest.hanbitCodingTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class hanbitCodingTest {

    /**
     * 그리디 문제
     * 각 자리가 숫자로만 이루어진 문자열 N가 주어졌을때 왼쪽부터 오른쪽으로 하나씩 모든 숫자를 확인하며 숫자 사이에 더하기 혹은 곱하기로 결과적으로 만들어질 수 있는 가장 큰 수를 구하는 프로그램입니다.
     * 각 연산은 순차적으로 왼쪽에서부터 이어져야 하며 예시는 다음과 같다. ((((0+2)x9)x8)x4) 의 형태로 계산이 되어야한다.
     */
    @Test
    public void 곱하기혹은더하기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int answer = 576;
        String n = "02984";
        String[] nArray = n.split("");

        int sum = Integer.parseInt(nArray[0]);

        for(int i=1; i<=nArray.length-1; i++) {
            int num = Integer.parseInt(nArray[i]);
            if(sum <= 1 || num <= 1) { //0이나 1이 있을 경우에는 더하기가 더 숫자가 크다 따라서 1이하 일때 더하기를 실행한다.
                sum += num;
            } else{ //1이하 보다 큰 즉 2이상의 숫자들은 곱하기가 더 크다. 따라서 2이상 일때 곱하기를 실행한다.
                sum *= num;
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(sum));
    }

    /**
     * 크기가 n만큼의 정사각형 공간이 제시 되었을때 상하좌우로 좌표를 이동하는 문제이다. 행렬로 좌표를 표시하였다.
     * 특이점은 보통의 컴퓨터 언어에서는 0,0부터 시작하지만 해당 문제에서는 1,1부터 좌표가 시작된다.
     * 시작점은 1,1 마지막 좌표는 5,5이다.
     * locateString가 R-R-R-U-D-D (left right up down) 다음과 같이 주어졌을때 우3 상1 하2 만큼 이동해야한다.
     * 상1 만큼 움직이게되면 좌표값을 벗어나기 때문에 이때는 좌표값 이동처리를 하지 않는다.
     */
    @Test
    public void 상하좌우이동() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String answer = "3,4";
        int n = 5;
        String locateString = "RRRUDD";
        List<String> locate = new ArrayList<>(Arrays.asList(locateString.split("")));
        int x = 1;
        int y = 1;

        //순서대로 조합하면 L,R,U,D
        //dx[0], dy[0] = L
        int[] dx = {0,0,-1,1};
        int[] dy = {-1,1,0,0};

        for (String str : locate) {
            int nx = 0;
            int ny = 0;

            //L,R,U,D도 dx, dy와 같이 매핑시켜줘도 된다. 그러면 for문에서 같은 index 값을 공유해서 처리할 수 있으나, for안에 또 for를 두면 시간이 좀 더 걸리지 않을까 해서 switch로 처리해 보았다..
            //솔직히 벡터(LRUD)가 4개뿐이라 for로 처리한다 해도 속도에는 엄청난 지장을 주진 않겠다.
            //생각해보니 switch로 처리할거면 dx, dy를 굳이 배열로 선언해놓을 필요는 없긴한것같다.. 다만 배열의 선언을 보고 매핑되는것으로 벡터값을 조금이나마? 쉽게 볼 수 있는 느낌이다.
            //코드 라인은 더 많이 늘어난 느낌이다..
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

            //범위에서 벗어나면 이동한 좌표를 처리하지 않는다.
            //1,1부터 시작이기때문에 0이나 n보다 큰 값이 나오면 좌표를 벗어난것으로 간주한다.
            if(nx <= 0 || ny <= 0 || nx > n || nx > n) continue;

            x = nx;
            y = ny;
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        assertThat(answer, is(x + "," + y));
    }

    /**
     * 주어진 n 만큼의 시간에서 존재하는 모든 HH:mm:ss의 형태에서 3이 하나라도 존재하면 cnt를 증가시킨다.
     * 하루는 24시간이기 때문에 총 86400가지의 경우의 수가 나온다.
     * 완전탐색으로 풀 수 있다.
     * n = 5 일때 00:00:00부터 05:59:59까지의 경우의 수를 검증하면 된다.
     */
    @Test
    public void 시간에서특정숫자찾기() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int answer = 11475;
        int n = 5;
        int cnt = 0;
        /*
        int totalSec = n * 60 * 60;

        for(int i=1; i<=totalSec; i++) {
            String hour = String.valueOf(TimeUnit.SECONDS.toHours(i));
            String minutes = String.valueOf(TimeUnit.SECONDS.toMinutes(i) % TimeUnit.HOURS.toMinutes(1));
            String second = String.valueOf(TimeUnit.SECONDS.toSeconds(i) % TimeUnit.MINUTES.toSeconds(1));

            if((hour + ":" + minutes + ":" + second).indexOf("3") > 0) cnt++;
        }
        으음.. 너무 가라로 처리하려 했던것같다. 우선 문제 이해를 잘못했다. 시간이 n만큼 주어지면 00시00분00초 ~ n시59분59초 까지 모두 탐색하는 구조였다..
        따라서 totalSec를 구하면 59분59초까지의 데이터가 누락되는 경우가 생겼다.
        시간을 변환해서 실제 시각으로 표현한 뒤 문자열로 변환해서 indexOf로 찾았는데 알고리즘 적으로 비효율적인듯 하다.

        밑에 풀이에서는 시간은 최대 24시까지기 때문에 입력받은 값 그대로, 분과초는 59까지이기 때문에 다중 for를 이용하여 문제를 해결했다.
        실제로 시간을 시각화해서 찾으려했던게 오히려 너무 꼬아서 생각한 듯 하다..
        알고리즘 문제를 많이 풀어보지 않은게 티가 난다.
        */

        for (int h=0; h<=n; h++) {
            for (int m=0; m<60; m++) {
                for (int s=0; s<60; s++) {
                    if(h % 10 == 3 || m / 10 == 3 || m % 10 == 3 || s / 10 == 3 || s % 10 == 3) cnt++;
                }
            }
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint() + "cnt: " + cnt);
        assertThat(answer, is(cnt));
    }

    /**
     * 대문자로 이루어진 알파벳과 숫자를 str로 제공할때 알파벳대문자 오름차순 정렬후 숫자는 모두 더하여 정렬된 알파벳 문자 뒤에 추가해준다.
     * 대문자로만 이루어진 알파벳이 제공된다.
     * 숫자는 데이터가 있으면 모두 더해 마지막에 문자열에 붙여준다.
     */
    @Test
    public void 문자열재정렬() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String answer = "ADDIJJJKKLSS20";
        String str = "AJKDLSI412K4JSJ9D";
        List<String> sStr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int intSum = 0;

        for (int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);

            //아스키 코드 값을 활용해 숫자와 대문자를 구분하였다.
            if (48 <= ch && ch <= 57) {
                intSum += Integer.parseInt(String.valueOf(ch));
            } else if(65 <= ch && ch <=90){
                sStr.add(String.valueOf(ch));
            }
        }

        //수집된 대문자를 오름차순으로 정렬한다.
        Collections.sort(sStr);

        for(String ss : sStr) {
            sb.append(ss);
        }

        //수집된 숫자 데이터가 있으면 정렬된 대문자 문자열 뒤에 붙여준다.
        if(intSum > 0) sb.append(intSum);

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds() + ", string: " + sb.toString());
        assertThat(answer, is(sb.toString()));
    }
}