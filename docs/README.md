# Ducky User Guide

Ducky is a simple app to help you keep track of tasks

# List of Commands

## Adding Tasks

### Todo

```todo <task_description>```

Adds a todo

### Deadline

```deadline <deadline_description> /by yyyy-MM-dd```

Adds a deadline that is by a certain date

### Event

```event <event_description> /from yyyy-MM-dd /to yyyy-MM-dd```

Adds an event that lasts from one date to another

## Listing tasks

### List

```List```

This lists all tasks

### Find

```find <match>```

This finds all relevant tasks that matches <match>

## Editing Tasks

Before running any of these commands, it is recommended you run `list` first to get the index of the task you're editing

### Mark

```mark <task_no>```

This masks the task as done. 
`<task_no>` is the number of the task, which can be seen by running `list`.

### Unmark

```unmark <task_no>```
`<task_no>` is the number of the task, which can be seen by running `list`.

### Delete

```delete <task_no>```
`<task_no>` is the number of the task, which can be seen by running `list`.
Note, this may alter the numbering of the tasks, so it is recommended you run `list` between deletions if you intend to delete several tasks.

## Bye

`bye`

Exists the app

## Autosaving

All tasks are saved locally immediately!

## Predictions

Unsure what to write next? Just look at the prediction box above where you type to see tips of what the next thing to type should be!