import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICiseAuthority, defaultValue } from 'app/shared/model/cise-authority.model';

export const ACTION_TYPES = {
  SEARCH_CISEAUTHORITIES: 'ciseAuthority/SEARCH_CISEAUTHORITIES',
  FETCH_CISEAUTHORITY_LIST: 'ciseAuthority/FETCH_CISEAUTHORITY_LIST',
  FETCH_CISEAUTHORITY: 'ciseAuthority/FETCH_CISEAUTHORITY',
  CREATE_CISEAUTHORITY: 'ciseAuthority/CREATE_CISEAUTHORITY',
  UPDATE_CISEAUTHORITY: 'ciseAuthority/UPDATE_CISEAUTHORITY',
  DELETE_CISEAUTHORITY: 'ciseAuthority/DELETE_CISEAUTHORITY',
  RESET: 'ciseAuthority/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICiseAuthority>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CiseAuthorityState = Readonly<typeof initialState>;

// Reducer

export default (state: CiseAuthorityState = initialState, action): CiseAuthorityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CISEAUTHORITIES):
    case REQUEST(ACTION_TYPES.FETCH_CISEAUTHORITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CISEAUTHORITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CISEAUTHORITY):
    case REQUEST(ACTION_TYPES.UPDATE_CISEAUTHORITY):
    case REQUEST(ACTION_TYPES.DELETE_CISEAUTHORITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CISEAUTHORITIES):
    case FAILURE(ACTION_TYPES.FETCH_CISEAUTHORITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CISEAUTHORITY):
    case FAILURE(ACTION_TYPES.CREATE_CISEAUTHORITY):
    case FAILURE(ACTION_TYPES.UPDATE_CISEAUTHORITY):
    case FAILURE(ACTION_TYPES.DELETE_CISEAUTHORITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CISEAUTHORITIES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISEAUTHORITY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISEAUTHORITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CISEAUTHORITY):
    case SUCCESS(ACTION_TYPES.UPDATE_CISEAUTHORITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CISEAUTHORITY):
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

const apiUrl = 'api/cise-authorities';
const apiSearchUrl = 'api/_search/cise-authorities';

// Actions

export const getSearchEntities: ICrudSearchAction<ICiseAuthority> = query => ({
  type: ACTION_TYPES.SEARCH_CISEAUTHORITIES,
  payload: axios.get<ICiseAuthority>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ICiseAuthority> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CISEAUTHORITY_LIST,
    payload: axios.get<ICiseAuthority>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICiseAuthority> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CISEAUTHORITY,
    payload: axios.get<ICiseAuthority>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICiseAuthority> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CISEAUTHORITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICiseAuthority> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CISEAUTHORITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICiseAuthority> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CISEAUTHORITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
