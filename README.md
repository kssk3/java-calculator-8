# 문자열 덧셈 계산기

---

## 기능 요구 사항

---
  
### 입력한 문자열에서 숫자를 추출하여 더하는 계산기를 구현한다.  
  
- 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한  
각 숫자의 합을 반환한다.
  - **예 : "" => 0, "1,2" => 3, "1,2,3" => 6, "1,2:3" => 6**
- 앞의 기본 구분자(쉼표, 콜론) 외에 커스텀 구분자를 지정할 수 있다.  
커스텀 구분자는 문자열 앞부분의 `"//"`와 `"\n"`사이에 위치하는 문자를 커스텀 구분자로 사용한다.  
  - 예를 들어 `"//;\n1;2;3"`과 같은 값을 입력할 경우 커스텀 구분자는 세미콜론`(;)`이며,  
    결과값은 6을 반환되어야 한다.
- 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`을 발생시킨 후 애플리케이션은 종료되어야 한다.  
  
--- 

## 입출력 요구 사항  
  
### 입력
- 구분자와 양수로 구성된 문자열

### 출력
- 덧셈 결과  
```
결과 : 6
```
  
### 실행 결과 예시  
```
덧셈할 문자열을 입력하시오.
1,2,3
결과 : 6
```

--- 
## 프로그래밍 요구 사항
- JDK 21 버전에서 실행 가능해야 한다.
- 프로그램 실행의 시작점은 `Application`의 `main()`이다.
- `bulid.gradle`파일은 변경할 수 없으, **제공된 라이브러리 이외의 라이브러리는 사용하지 않는다**
- 프로그램 종료시 `System.exit()`를 호출하지 않는다.
- 프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일,패키지 등의 이름을 바꾸거나 이동하지 않는다.
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.

---

## 라이브러리
- `camp.nextstep.edu.missionutils`에서 제공하는 Console API를 사용하여 구현해야 한다.
  - 사용자가 입력하는 값은 `camp.nextstep.edu.missionutils.Console`의 readLine()을 활용한다.

---

## 구현할 기능 목록
- 문자열을 입력받는다.
- `,`와 `;`을 통해 숫자를 구분한다.
- 음수 입력 불가
- 숫자의 총합을 구한다.
- 커스텀 구분자를 사용할 수 있다.
- 잘못된 값이 입력되면 `IllegalArgumentException`을 발생시킨다.

## 문제점 및 해결 방안 

1. 이스케이프 문자 `\\n`과 실제 개행문자 `\n` 구분
```
테스트 케이스 1: 백슬래시 2번 (\\n) - 통과해야 함
"//;\\n1;2;3" -> 결과: 6

테스트 케이스 2: 백슬래시 1번 (\n) - 예외 발생해야 함
"//;\n1;2;3" -> IllegalArgumentException
```

- `"\\n"` = 백슬래시 + n(2글자 문자열)
- `"\n"` = 실제 개행문자 (1글자)

### 해결 방법
```java
private static DelimiterInfo extraDelimiter(String input) {
    // 1순위 : 이스케이프된 \n 체크 (정상 케이스)
        int escapeIndex = input.indexOf(ESCAPE_NEWLINE); // "\\n" 찾기
        
        if(escapeIndex != -1) {
            String customDelimiter = input.substring(PREFIX.length(), escapeIndex);
            String numbers = input.substring(escapeIndex + ESCAPE_NEWLINE.length());
            return new DelimiterInfo(customDelimiter, numbers, false);
        }
    // 2순위 : 실제 개행문자 체크 (예외 케이스)
        int newLineIndex = input.indexOf(NEW_LINE); // "\n" 찾기 
        if (newLineIndex != -1) {
            String customDelimiter = input.substring(PREFIX.length(), newLineIndex);
            String numbers = input.substring(newLineIndex + NEW_LINE.length());
            return new DelimiterInfo(customDelimiter, numbers, true);
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
```

### 검증 로직
```java
if (info.userNewLine) {
    throw new IllegalArgumentException(EXCEPTION_MESSAGE);
}
```

2. 연속된 구분자 처리 (빈 토큰 제거)
  
### 문제 상황
```Java
"1,,2,,3"      → ["1", "", "2", "", "3"]  // 빈 문자열 발생
"//;;\\n1;;2;;3" → ["1", "", "2", "", "3"]  // 연속 구분자
```

### 문제 해결  
```java
private static List<Integer> parseValue(String delimiter, String line) {
        List<Integer> result = new ArrayList<>();
        
        String[] tokens = line.split(delimiter);
        for (String token : tokens) {
            // 빈 토큰은 건너뛰기
            if (token.isEmpty()) continue;
            try {
                result.add(Integer.parseInt(token));
                if (result.getLast() <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }
        }

        return result;
    }
```
  
3. 특수문자 구분자 처리  

### 문제 상황  
```java
"//..\\n1..2..3"  // . 은 정규표현식에서 "모든 문자"를 의미
"//**\\n1**2**3"  // * 은 정규표현식에서 "0회 이상 반복"을 의미
```
`split(".")`을 하면 모든 문자가 구분자가 되어 잘못된 결과 발생  
  
### 해결 방법
```java
delimiter = DEFAULT_DELIMITER + "|" + Pattern.quote(info.delimiter);
```  
  
`Pattern.quote()'
```java
Pattern.quote("..")  → "\\Q..\\E"  // 리터럴 문자열로 처리
Pattern.quote("**")  → "\\Q**\\E"
Pattern.quote(";;")  → "\\Q;;\\E"
```