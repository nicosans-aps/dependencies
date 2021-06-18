package dependencies;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Cette classe est chargée de parser les arguments de la ligne de commande dans
 * le but d'effectuer une analyse d'un dossier projet Les paramètres
 * obligatoires de la ligne de commande sont : - le chemin du projet à analyser
 * - une liste de jar constituant les points d'entrée de l'application
 * 
 * @author nicol
 *
 */
public class Dependencies {
	private static PrintStream output = System.out;

	/**
	 * Retourne l'objet Options contenant la liste des options utilisées par le
	 * parser de la ligne de commande
	 * 
	 * @return Options
	 */
	private static Options configParameters() {
		final Option projectPathOption = Option.builder("p").longOpt("projectpath").desc("Chemin du projet à analyser")
				.hasArg(true).argName("projectPathString").required(true).build();

		final Option projectJarsOption = Option.builder("j").longOpt("jarpaths")
				.desc("Chemins des fichiers jar à analyser").hasArg(true).argName("projectJarsString").required(true)
				.build();
		Options options = new Options();
		options.addOption(projectJarsOption);
		options.addOption(projectPathOption);
		return options;
	}

	/**
	 * Redéfini l'objet PrintStream utilisé pour écrire vers la console. La
	 * redéfinition est utilisée lors de l'exécution des tests unitaires. output est
	 * défini à System.out par défaut
	 */
	public static void setOutputStream(PrintStream outputStream) {
		output = outputStream;

	}

	public static void main(String[] args) {

		final Options options = configParameters();

		final CommandLineParser parser = new DefaultParser();

		try {
			final CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("p")) {
				output.println("p option was used with value " + cmd.getOptionValue("p"));
			}
			if (cmd.hasOption("j")) {
				output.println("j option was used with value " + cmd.getOptionValue("j"));
			}

		} catch (MissingOptionException e) {
			output.println("Argument manquant");
			System.exit(0);

		} catch (ParseException e) {
			output.println("Erreur lors de la lecture des arguments de la ligne de commande.");
			e.printStackTrace();
			System.exit(1);

		}

		// File projectRootFolder = new File();
		// Set<File> projectJars = new Set();
		// DependenciesAnalizer da = new DependenciesAnalizer(projectRootFolder,
		// projectJars);

	}

}
