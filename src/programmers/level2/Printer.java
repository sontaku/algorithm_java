//  문제 설명
//  일반적인 프린터는 인쇄 요청이 들어온 순서대로 인쇄합니다. 
//  그렇기 때문에 중요한 문서가 나중에 인쇄될 수 있습니다. 
//  이런 문제를 보완하기 위해 중요도가 높은 문서를 먼저 인쇄하는 프린터를 개발했습니다. 
//  이 새롭게 개발한 프린터는 아래와 같은 방식으로 인쇄 작업을 수행합니다.
//
//  1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
//  2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
//  3. 그렇지 않으면 J를 인쇄합니다.
//  예를 들어, 4개의 문서(A, B, C, D)가 순서대로 인쇄 대기목록에 있고
//  중요도가 2 1 3 2 라면 C D A B 순으로 인쇄하게 됩니다.
//
//  내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 알고 싶습니다. 위의 예에서 C는 1번째로, A는 3번째로 인쇄됩니다.
//
//  현재 대기목록에 있는 문서의 중요도가 순서대로 담긴 배열 priorities와 
//  내가 인쇄를 요청한 문서가 현재 대기목록의 어떤 위치에 있는지를 알려주는 location이 매개변수로 주어질 때, 
//  내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 return 하도록 solution 함수를 작성해주세요.
//
//  제한사항
//  현재 대기목록에는 1개 이상 100개 이하의 문서가 있습니다.
//  인쇄 작업의 중요도는 1~9로 표현하며 숫자가 클수록 중요하다는 뜻입니다.
//  location은 0 이상 (현재 대기목록에 있는 작업 수 - 1) 이하의 값을 가지며 대기목록의 가장 앞에 있으면 0, 두 번째에 있으면 1로 표현합니다.
//  입출력 예
//  priorities	location	return
//  [2, 1, 3, 2]	2	1
//  [1, 1, 9, 1, 1, 1]	0	5
//  입출력 예 설명
//  예제 #1
//
//  문제에 나온 예와 같습니다.
//
//  예제 #2
//
//  6개의 문서(A, B, C, D, E, F)가 인쇄 대기목록에 있고 
//  중요도가 1 1 9 1 1 1 이므로 C D E F A B 순으로 인쇄합니다.
package programmers.level2;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class Printer {

	public static class PrintDoc {
		// idx <- 인덱스
		// priority <- priorities 값
		int idx;
		int priority;

		PrintDoc(int idx, int priority) {
			this.idx = idx;
			this.priority = priority;
		}
	}

	// 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 !
	//
	// 문서의 중요도가 순서대로 담긴 배열 priorities
	// 인쇄를 요청한 문서가 현재 대기목록의 인덱스 location
	public static int solution(int[] priorities, int location) {
		int answer = 1;

		// step1. from priorities to LinkedList
		LinkedList<PrintDoc> list = new LinkedList<>();

		// array : [2, 1, 3, 2]

		// link : [0:2 - 1:1 - 2:3 - 3:2]
		// [2:3 - 3:2 - 0:2 - 1:1]

		for (int i = 0; i < priorities.length; i++) {
			list.add(new PrintDoc(i, priorities[i]));
		}

//		System.out.println("0번의 idx : " + list.get(0).idx);
//		System.out.println("0번의 pri : " + list.get(0).priority);
//		System.out.println("1번의 idx : " + list.get(1).idx);
//		System.out.println("1번의 pri : " + list.get(1).priority);
//		System.out.println("2번의 idx : " + list.get(2).idx);
//		System.out.println("2번의 pri : " + list.get(2).priority);

		// step2. 해당 인덱스의 값이 최대값인지
		while (list.size() > 1) {
			PrintDoc first = list.getFirst();
			for (int i = 1; i < list.size(); i++) {

				// [1, 1, 9, 1, 1, 1]
				// 1 < 9
				// [1, 1, 9, 1, 1, 1, 1(new)]
				// [1, 9, 1, 1, 1, 1(new)]
				// 
				// [1, 9, 1, 1, 1, 1] => 최종 [9, 1, 1, 1, 1, 1]
				if (first.priority < list.get(i).priority) {
					list.addLast(first);
					list.removeFirst();
					break;
				}

				if (i == list.size() - 1) {
					if (first.idx == location)
						return answer;
					list.removeFirst();
					// [1, 1, 1, 1, 1]
					answer++;
				}
			}
		}
		return answer;
	}

	public static void main(String[] args) {
		int[] priorities = { 2, 1, 3, 2 };
		int location = 2;
		int[] priorities2 = { 1, 1, 9, 1, 1, 1 };
		int location2 = 0;
		System.out.println(solution(priorities, location));
//		solution(ex1, ex1Loc);
	}
}
