## Homework2
**MET CS622**

**Tianyi Jin**

**Sep 29, 2021**

Source code: https://github.com/Kavinjsir/indiegogo/

## Get Started

### Configuration

This project is initialized through vscode java template. Any other IDE such as eclipse or idea has not been verified to start with. So it is recommended to open this project in vscode.

#### Vscode Java Env Setup
Vscode provides a one-step Java pack to start with java projects in simple process.
The pack is provided at: https://code.visualstudio.com/docs/java/java-tutorial#_coding-pack-for-java .

All you need to do is just download the pack, open it and follow the instructions.

Once the installation is finished, open vscode, the landing page should give you hints on how to create a java project.

For our case, we don't need to create a new one. Just go to the menu bar of vscode and click **open..**, then choose the directoy of our project.

Once clicked, the Vscode Java pack will automatically recognize the project and switch the env for you.

#### Add additional jar
This project requires two external jar packeges:
1. commons-lang3-3.1.jar
2. opencsv-5.5.2.jar

They are specified in `.vscode/settings.json`.

In Vscode Explorer, expend the `Java Projects` column, these jar should be displayed under the `Referenced Libraries`. If there is empty, download the jar, and click the `+` button beside the `Referenece Libraries` text, load the jar from what you've downloaded. 

#### Run the project
The project can be ran through Vscode Java Tester Runtime.

To do that, just click the class file `Indiegogo` in the file explorer, a "run button" should be displayed on the right corner of the window, click that and the program will be launched. A panel should be automatically displayed and you can see the result there.

> In Line 7 of `Indiegogo.class`, a static list of input file paths is specified. To make the program work, either import csv files based on the value in the list or change the code of the value to simplify.

## TODO
1. Run the app, start loading data from sources and generate output file.
2. When step 1 completed, print a message include output file path.
3. Print a message for either search by keyword or check search records.
   - 1. Search by inputting a keyword
   - 2. Check keyword search record
   - "q" for exit

## Project Structure

This project is initialized through vscode java template.

### CSV Template
- bullet_point,
- category,
- category_url,
- clickthrough_url,
- close_date,
- currency,
- funds_raised_amount,
- funds_raised_percent,
- image_url,
- is_indemand,
- is_pre_launch,
- offered_by,
- open_date,
- perk_goal_percentage,
- perks_claimed,
- price_offered,
- price_retail,
- product_stage,
- project_id,
- project_type,
- source_url,
- tagline,
- tags,
- title

## Description By Vscode

### Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
