package innovitics.azimut.utilities.fileutilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

public final class FileReaderProp {

	public static String readUnicodeJava11(String fileName) {

		Path path = Paths.get(fileName);
		StringBuffer stringBuffer=new StringBuffer();
		try (FileReader fr = new FileReader(fileName, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(fr)) {

			String str;
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
				System.out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
}