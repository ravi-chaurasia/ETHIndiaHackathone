# Steps To Run
 - Create virtual env and install packages in requirements file.
   - `virtualenv venv`
   - `source venv/bin/activate`
   - `pip install -r requirements.txt`
 - Replace the `API_KEY` secrets_dune with original api key.
 - Run get_dunes_data with the query id as argument.
   - Sample - `python get_dunes_data.py 1698243`