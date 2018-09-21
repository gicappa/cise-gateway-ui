import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICiseService, defaultValue } from 'app/shared/model/cise-service.model';

export const ACTION_TYPES = {
  SEARCH_CISESERVICES: 'ciseService/SEARCH_CISESERVICES',
  FETCH_CISESERVICE_LIST: 'ciseService/FETCH_CISESERVICE_LIST',
  FETCH_CISESERVICE: 'ciseService/FETCH_CISESERVICE',
  CREATE_CISESERVICE: 'ciseService/CREATE_CISESERVICE',
  UPDATE_CISESERVICE: 'ciseService/UPDATE_CISESERVICE',
  DELETE_CISESERVICE: 'ciseService/DELETE_CISESERVICE',
  RESET: 'ciseService/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICiseService>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CiseServiceState = Readonly<typeof initialState>;

// Reducer

export default (state: CiseServiceState = initialState, action): CiseServiceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CISESERVICES):
    case REQUEST(ACTION_TYPES.FETCH_CISESERVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CISESERVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CISESERVICE):
    case REQUEST(ACTION_TYPES.UPDATE_CISESERVICE):
    case REQUEST(ACTION_TYPES.DELETE_CISESERVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CISESERVICES):
    case FAILURE(ACTION_TYPES.FETCH_CISESERVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CISESERVICE):
    case FAILURE(ACTION_TYPES.CREATE_CISESERVICE):
    case FAILURE(ACTION_TYPES.UPDATE_CISESERVICE):
    case FAILURE(ACTION_TYPES.DELETE_CISESERVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CISESERVICES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISESERVICE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISESERVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CISESERVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_CISESERVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CISESERVICE):
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

const apiUrl = 'api/cise-services';
const apiSearchUrl = 'api/_search/cise-services';

// Actions

export const getSearchEntities: ICrudSearchAction<ICiseService> = query => ({
  type: ACTION_TYPES.SEARCH_CISESERVICES,
  payload: axios.get<ICiseService>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ICiseService> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CISESERVICE_LIST,
    payload: axios.get<ICiseService>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICiseService> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CISESERVICE,
    payload: axios.get<ICiseService>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICiseService> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CISESERVICE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICiseService> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CISESERVICE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICiseService> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CISESERVICE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
