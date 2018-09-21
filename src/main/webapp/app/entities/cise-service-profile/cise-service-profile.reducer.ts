import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICiseServiceProfile, defaultValue } from 'app/shared/model/cise-service-profile.model';

export const ACTION_TYPES = {
  SEARCH_CISESERVICEPROFILES: 'ciseServiceProfile/SEARCH_CISESERVICEPROFILES',
  FETCH_CISESERVICEPROFILE_LIST: 'ciseServiceProfile/FETCH_CISESERVICEPROFILE_LIST',
  FETCH_CISESERVICEPROFILE: 'ciseServiceProfile/FETCH_CISESERVICEPROFILE',
  CREATE_CISESERVICEPROFILE: 'ciseServiceProfile/CREATE_CISESERVICEPROFILE',
  UPDATE_CISESERVICEPROFILE: 'ciseServiceProfile/UPDATE_CISESERVICEPROFILE',
  DELETE_CISESERVICEPROFILE: 'ciseServiceProfile/DELETE_CISESERVICEPROFILE',
  RESET: 'ciseServiceProfile/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICiseServiceProfile>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CiseServiceProfileState = Readonly<typeof initialState>;

// Reducer

export default (state: CiseServiceProfileState = initialState, action): CiseServiceProfileState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CISESERVICEPROFILES):
    case REQUEST(ACTION_TYPES.FETCH_CISESERVICEPROFILE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CISESERVICEPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CISESERVICEPROFILE):
    case REQUEST(ACTION_TYPES.UPDATE_CISESERVICEPROFILE):
    case REQUEST(ACTION_TYPES.DELETE_CISESERVICEPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CISESERVICEPROFILES):
    case FAILURE(ACTION_TYPES.FETCH_CISESERVICEPROFILE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CISESERVICEPROFILE):
    case FAILURE(ACTION_TYPES.CREATE_CISESERVICEPROFILE):
    case FAILURE(ACTION_TYPES.UPDATE_CISESERVICEPROFILE):
    case FAILURE(ACTION_TYPES.DELETE_CISESERVICEPROFILE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CISESERVICEPROFILES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISESERVICEPROFILE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISESERVICEPROFILE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CISESERVICEPROFILE):
    case SUCCESS(ACTION_TYPES.UPDATE_CISESERVICEPROFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CISESERVICEPROFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cise-service-profiles';
const apiSearchUrl = 'api/_search/cise-service-profiles';

// Actions

export const getSearchEntities: ICrudSearchAction<ICiseServiceProfile> = query => ({
  type: ACTION_TYPES.SEARCH_CISESERVICEPROFILES,
  payload: axios.get<ICiseServiceProfile>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ICiseServiceProfile> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CISESERVICEPROFILE_LIST,
  payload: axios.get<ICiseServiceProfile>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICiseServiceProfile> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CISESERVICEPROFILE,
    payload: axios.get<ICiseServiceProfile>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICiseServiceProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CISESERVICEPROFILE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICiseServiceProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CISESERVICEPROFILE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICiseServiceProfile> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CISESERVICEPROFILE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
