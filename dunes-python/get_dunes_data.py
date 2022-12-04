from secrets_dunes import API_KEY
from requests import get, post
import pandas as pd
import json
import sys

HEADER = {"x-dune-api-key": API_KEY}
BASE_URL = "https://api.dune.com/api/v1/"
COMPLETE_QUERY_STATUS = "QUERY_STATE_COMPLETED"
DUNES_JSON_FILE_PATH = "../src/main/resources/DunesData.json"


def make_api_url(module, action, ID):
    """
    We shall use this function to generate a URL to call the API.
    """

    url = BASE_URL + module + "/" + ID + "/" + action

    return url


def execute_query(query_id):
    """
    Takes in the query ID.
    Calls the API to execute the query.
    Returns the execution ID of the instance which is executing the query.
    """

    url = make_api_url("query", "execute", query_id)
    response = post(url, headers=HEADER)
    execution_id = response.json()['execution_id']

    return execution_id


def get_query_status(execution_id):
    """
    Takes in an execution ID.
    Fetches the status of query execution using the API
    Returns the status response object
    """

    url = make_api_url("execution", "status", execution_id)
    response = get(url, headers=HEADER)

    return response


def get_query_results(execution_id):
    """
    Takes in an execution ID.
    Fetches the results returned from the query using the API
    Returns the results response object
    """

    url = make_api_url("execution", "results", execution_id)
    response = get(url, headers=HEADER)

    return response


def cancel_query_execution(execution_id):
    """
    Takes in an execution ID.
    Cancels the ongoing execution of the query.
    Returns the response object.
    """

    url = make_api_url("execution", "cancel", execution_id)
    response = get(url, headers=HEADER)

    return response


def check_if_query_executed(execution_id):
    initial_query_status = ""
    max_tries = 10
    tries_count = 0
    while (initial_query_status != COMPLETE_QUERY_STATUS):
        try:
            response = get_query_status(execution_id)
            query_status_json = response.json()
            initial_query_status = query_status_json.get('state')
        except Exception as e:
            if tries_count == max_tries:
                raise Exception("The dunes query did not run due to ", e)
        tries_count += 1


def get_query_result_json(query_id):
    execution_id = execute_query(str(query_id))
    check_if_query_executed(execution_id)
    dunes_query_response = get_query_results(execution_id)
    dunes_json_object = json.dumps(dunes_query_response.json(), indent=4)
    with open(DUNES_JSON_FILE_PATH, "w") as outfile:
        outfile.write(dunes_json_object)


get_query_result_json(sys.argv[1])
