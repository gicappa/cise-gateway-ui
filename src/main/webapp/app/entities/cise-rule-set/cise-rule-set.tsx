import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './cise-rule-set.reducer';
import { ICiseRuleSet } from 'app/shared/model/cise-rule-set.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseRuleSetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ICiseRuleSetState {
  search: string;
}

export class CiseRuleSet extends React.Component<ICiseRuleSetProps, ICiseRuleSetState> {
  state: ICiseRuleSetState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { ciseRuleSetList, match } = this.props;
    return (
      <div>
        <h2 id="cise-rule-set-heading">
          <Translate contentKey="gatewayuiApp.ciseRuleSet.home.title">Cise Rule Sets</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayuiApp.ciseRuleSet.home.createLabel">Create new Cise Rule Set</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('gatewayuiApp.ciseRuleSet.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseRuleSet.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseRuleSet.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseRuleSet.ciseAuthority">Cise Authority</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ciseRuleSetList.map((ciseRuleSet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${ciseRuleSet.id}`} color="link" size="sm">
                      {ciseRuleSet.id}
                    </Button>
                  </td>
                  <td>{ciseRuleSet.name}</td>
                  <td>{ciseRuleSet.description}</td>
                  <td>
                    {ciseRuleSet.ciseAuthorityId ? (
                      <Link to={`cise-authority/${ciseRuleSet.ciseAuthorityId}`}>{ciseRuleSet.ciseAuthorityId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ciseRuleSet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ciseRuleSet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ciseRuleSet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ ciseRuleSet }: IRootState) => ({
  ciseRuleSetList: ciseRuleSet.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseRuleSet);
