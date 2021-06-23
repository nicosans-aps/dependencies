package dependencies;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
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
	private static Options options = configParameters();

	/**
	 * Retourne l'objet Options contenant la liste des options utilisées par le
	 * parser de la ligne de commande
	 * 
	 * @return Options
	 */
	private static Options configParameters() {
		final Option projectPathOption = Option.builder("p").longOpt("projectpath").desc("Chemin du projet à analyser")
				.hasArg(true).argName("projectPathString").required(false).build();

		final Option projectJarsOption = Option.builder("j").longOpt("jarpaths")
				.desc("Chemins du ou des fichiers jar à analyser (<CHEMIN1>;<CHEMIN2>;...").hasArg(true)
				.argName("projectJarsString").required(false).valueSeparator(';').build();

		final Option helpOption = Option.builder("h").longOpt("help").desc("Cette page d'aide").hasArg(false)
				.required(false).build();

		Options options = new Options();
		options.addOption(projectJarsOption);
		options.addOption(projectPathOption);
		options.addOption(helpOption);
		return options;
	}

	/**
	 * Affiche le message d'aide sur la sortie standard
	 */
	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		PrintWriter printWriter = new PrintWriter(output);
		formatter.printHelp(printWriter, 80, " ", "", options, 0, 0, "");
		printWriter.flush();
	}

	private static void analize(String projectPath, String[] jarFiles) {
		DependenciesAnalizer da = new DependenciesAnalizer(projectPath, jarFiles);
		try {

			da.analize();

		} catch (DependenciesAnalizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			if (cmd.hasOption('h') || cmd.getOptions().length == 0) {
				printHelp();
			}

			else if (!cmd.hasOption('j')) {
				output.println("L'argument -j correspondant au chemin du ou des fichiers .jar racines est manquant.");
			}

			else if (!cmd.hasOption('p')) {
				output.println("L'argument -p correspondant au chemin du projet à analyser est manquant.");

			} else {
				analize(cmd.getOptionValue('p'), cmd.getOptionValues('j'));
			}

		} catch (MissingOptionException e) {
			printHelp();

		} catch (ParseException e) {
			output.println("Erreur lors de la lecture des arguments de la ligne de commande.");
			e.printStackTrace();
		}

	}

}
